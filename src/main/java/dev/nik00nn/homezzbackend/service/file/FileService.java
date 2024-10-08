package dev.nik00nn.homezzbackend.service.file;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.ProfilePhoto;
import dev.nik00nn.homezzbackend.repository.IFileRepository;
import dev.nik00nn.homezzbackend.repository.IProfilePhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileService implements IFileService {

    private final IFileRepository fileRepository;
    private final IProfilePhotoRepository profilePhotoRepository;

    public FileService(IFileRepository fileRepository, IProfilePhotoRepository profilePhotoRepository) {
        this.fileRepository = fileRepository;
        this.profilePhotoRepository = profilePhotoRepository;
    }

    @Override
    public File getImageById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public File saveImage(MultipartFile file) throws IOException {
        File fileToCreate = new File();

        fileToCreate.setFileName(file.getOriginalFilename());
        fileToCreate.setCreationDate(LocalDate.now());
        fileToCreate.setFileContent(file.getBytes());
        fileToCreate.setFileTitle(file.getName());

        return fileRepository.save(fileToCreate);
    }

    @Override
    public File checkAndSaveImage(MultipartFile file) throws IOException {
        File existingFile = fileRepository.findByFileName(file.getOriginalFilename());

        if(existingFile != null) {
            return existingFile;
        } else{
            return saveImage(file);
        }
    }

    @Override
    public ProfilePhoto saveProfilePhoto(MultipartFile file) throws IOException {
        ProfilePhoto photoToCreate = new ProfilePhoto();

        photoToCreate.setFilename(file.getOriginalFilename());
        photoToCreate.setCreationDate(LocalDate.now());
        photoToCreate.setFileContent(file.getBytes());
        photoToCreate.setFileTitle(file.getName());

        return profilePhotoRepository.save(photoToCreate);
    }
}
