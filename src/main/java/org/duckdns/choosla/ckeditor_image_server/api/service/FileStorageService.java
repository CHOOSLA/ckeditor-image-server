package org.duckdns.choosla.ckeditor_image_server.api.service;

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
    private final Path fileStroageLoaction;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStroageLoaction = Path.of(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
           Files.createDirectories(this.fileStroageLoaction);
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
        Path target = this.fileStroageLoaction.resolve(newName);
        try{
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BadIOExecption(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복사 중 오류 발생");
        }


        return "/uploads/" + newName;
    }
}
