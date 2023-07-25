package com.file.storage;

import com.file.storage.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioBucketCreationListener {
    private final BucketService bucketService;

    @EventListener
    public void createInitialBucket(ContextRefreshedEvent event) {
        String bucketName = "user-files";
        try {
            if (!bucketService.isBucketExists(bucketName)) {
                bucketService.createBucket(bucketName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Initial bucket is not created", e);
        }
    }
}
