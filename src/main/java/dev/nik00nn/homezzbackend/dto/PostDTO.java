package dev.nik00nn.homezzbackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private LocalDate creationDate;
    private String title;
    private String description;
    private Double price;
    private int numberOfRooms;
    private int usefulSurface;
    private int constructionYear;
    private PostType type;
    private PropertyType propertyType;
    private List<File> photos;
}
