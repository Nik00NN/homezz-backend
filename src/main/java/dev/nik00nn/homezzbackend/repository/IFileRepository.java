package dev.nik00nn.homezzbackend.repository;

import dev.nik00nn.homezzbackend.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<File,Long> {
    File findByFileName(String fileName);
}
