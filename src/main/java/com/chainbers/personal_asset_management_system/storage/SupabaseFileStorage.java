package com.chainbers.personal_asset_management_system.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Component
public class SupabaseFileStorage {

    @Value("${supabase.url}")
    private String url;

    @Value("${supabase.service-key}")
    private String serviceKey;

    @Value("${supabase.bucket}")
    private String bucket;

    private final RestClient restClient = RestClient.create();

    public String store(String folder, MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String contentType = file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("File content type is missing");
        }

        String path = folder + "/" + UUID.randomUUID();

        restClient.post()
                .uri(url + "/storage/v1/object/{bucket}/{path}", bucket, path)
                .header("apikey", serviceKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceKey)
                .contentType(MediaType.parseMediaType(contentType))
                .body(file.getResource())
                .retrieve()
                .toBodilessEntity();

        return path;
    }

    public String signedUrl(String path) {

        Map<?, ?> response = restClient.post()
                .uri(url + "/storage/v1/object/sign/{bucket}/{path}", bucket, path)
                .header("apikey", serviceKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("expiresIn", 3600))
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("signedURL") == null) {
            throw new IllegalStateException("Failed to generate signed URL");
        }

        String signedURL = response.get("signedURL").toString();

        return signedURL.startsWith("http") ? signedURL : url + "/storage/v1" + signedURL;
    }
}
