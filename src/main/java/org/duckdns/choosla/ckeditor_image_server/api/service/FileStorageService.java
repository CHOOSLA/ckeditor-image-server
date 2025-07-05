package org.duckdns.choosla.ckeditor_image_server.api.service;

import net.coobird.thumbnailator.Thumbnails;
import org.duckdns.choosla.ckeditor_image_server.common.config.FileStorageProperties;
import org.duckdns.choosla.ckeditor_image_server.common.exception.BadIOExecption;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;


@Service
public class FileStorageService {
    private final Path originDir;
    private final Path thumbDir;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.originDir = Path.of(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.thumbDir  = originDir.resolve("thumb");
        try{
           Files.createDirectories(this.originDir);
           Files.createDirectories(thumbDir);
        } catch (IOException e) {
            throw new BadIOExecption(HttpStatus.INTERNAL_SERVER_ERROR, "경로를 생성하는 중 오류 발생");
        }
    }

    public String storeFile(MultipartFile file){
        // 파일명 검증
        String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // 랜덤 UUID + 확장자
        String newName = UUID.randomUUID().toString() + original.substring(original.lastIndexOf("."));

        // 파일 복사
        Path originalTarget = this.originDir.resolve(newName);
        try{
            Files.copy(file.getInputStream(), originalTarget, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BadIOExecption(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복사 중 오류 발생");
        }

        // 썸네일 생성
        Path thumbTarget = thumbDir.resolve(newName);
        try {
            Thumbnails.of(originalTarget.toFile())
                      .size(200, 200)          // 긴 변 기준 200px, 비율 유지
                      .keepAspectRatio(true)
                      .toFile(thumbTarget.toFile());
        } catch (IOException e) {
            throw new BadIOExecption(HttpStatus.INTERNAL_SERVER_ERROR, "썸네일 생성 실패");
        }


        return "/uploads/" + newName;
    }
}
