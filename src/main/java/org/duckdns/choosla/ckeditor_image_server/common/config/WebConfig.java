package org.duckdns.choosla.ckeditor_image_server.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")// file:uploads/ 같은 prefix 없이 디렉터리 절대경로만 써도 된다.
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로 허용
                .allowedOrigins("http://localhost:5200","http://0.0.0.0:5200") // Vite 프론트엔드 origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키 허용 시 필요
                .maxAge(3600); // preflight 캐싱 시간 (초)
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 꼭 끝에 '/'를 붙여야 한다.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + (uploadDir.endsWith("/") ? uploadDir : uploadDir + "/"));
    }
}
