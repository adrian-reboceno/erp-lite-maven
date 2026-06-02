package com.ikigaisoftware.erp_lite.domain.product;

import java.net.URI;
import java.net.URISyntaxException;


public record ProductImage(String imageUrl) {

    public ProductImage {
        if (imageUrl == null) {
            throw new IllegalArgumentException("Image URL cannot be null");
        }
        validateUrl(imageUrl);
    }

    private static void validateUrl(String url) {
        try {
            URI uri = new URI(url);
            if (!uri.isAbsolute()) {
                throw new IllegalArgumentException("URL must be absolute: " + url);
            }
            if (uri.getScheme() == null || (!uri.getScheme().equals("http") && !uri.getScheme().equals("https"))) {
                throw new IllegalArgumentException("URL must use http or https scheme: " + url);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format: " + url, e);
        }
    }

    public static ProductImage of(String imageUrl) {
        return new ProductImage(imageUrl);
    }

    public String getFullUrl() {
        return imageUrl;
    }

    public String getFileName() {
        int lastSlashIndex = imageUrl.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < imageUrl.length() - 1) {
            return imageUrl.substring(lastSlashIndex + 1);
        }
        return imageUrl;
    }
}
