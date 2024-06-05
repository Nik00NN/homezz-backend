package dev.nik00nn.homezzbackend.service.file;

import dev.nik00nn.homezzbackend.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    File getImageById(Long id);
    File saveImage(MultipartFile file) throws IOException;
    File checkAndSaveImage(MultipartFile file) throws IOException;
}
