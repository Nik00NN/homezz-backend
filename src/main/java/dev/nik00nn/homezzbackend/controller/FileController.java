package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.service.file.FileService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        File image = fileService.getImageById(imageId);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        String imageName = image.getFileName();
        headers.setContentType(MediaTypeFactory.getMediaType(imageName).orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(imageName)
                        .build()
        );

        headers.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)
                .noTransform()
                .mustRevalidate());
        headers.setAccessControlExposeHeaders(Collections.singletonList(HttpHeaders.CONTENT_DISPOSITION));
        return new ResponseEntity<>(image.getFileContent(), headers, HttpStatus.OK);
    }
}
