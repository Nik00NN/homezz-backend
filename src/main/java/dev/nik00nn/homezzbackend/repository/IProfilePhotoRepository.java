package dev.nik00nn.homezzbackend.repository;

import dev.nik00nn.homezzbackend.domain.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
}
