package org.duckdns.choosla.ckeditor_image_server.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Getter @Setter
public class FileStorageProperties {
    private String uploadDir;
}
