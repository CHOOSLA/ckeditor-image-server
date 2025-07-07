package org.duckdns.choosla.ckeditor_image_server.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.duckdns.choosla.ckeditor_image_server.api.dto.UploadResponse;
import org.duckdns.choosla.ckeditor_image_server.api.service.FileStorageService;
import org.duckdns.choosla.ckeditor_image_server.common.response.ApiResponse;
import org.duckdns.choosla.ckeditor_image_server.common.response.SuccessStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@Slf4j
public class ImageUploadController {
    private final FileStorageService fileStorageService;

    public ImageUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<UploadResponse>> uploadImage(@RequestParam("upload") MultipartFile file) {
        String url = fileStorageService.storeFile(file);
        log.info("이미지 업로드에 성공했습니다 url: {}", url);
        UploadResponse uploadResponse = new UploadResponse(url);
        return ApiResponse.success(SuccessStatus.IMAGE_UPLOAD_SUCCESS, uploadResponse);
    }
}
