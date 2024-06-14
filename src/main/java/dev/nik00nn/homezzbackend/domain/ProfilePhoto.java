package dev.nik00nn.homezzbackend.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profilePhotos")
public class ProfilePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String filename;

    @Column(name = "file_title", nullable = false)
    private String fileTitle;

    @Column(name = "file_content", nullable = false)
    private byte[] fileContent;

    @Column(name = "file_creation_date", nullable = false)
    private LocalDate creationDate;

}
