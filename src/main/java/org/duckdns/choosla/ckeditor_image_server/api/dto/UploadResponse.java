package org.duckdns.choosla.ckeditor_image_server.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadResponse {
    private String url;

    public UploadResponse(String url) {
        this.url = url;
    }
}
