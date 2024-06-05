package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.service.file.FileService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId){
        File image = fileService.getImageById(imageId);
        if(image == null){
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
        return new ResponseEntity<>(image.getFileContent(),headers,HttpStatus.OK);
    }
}
