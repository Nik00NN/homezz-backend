package dev.nik00nn.homezzbackend.dto;


import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetailsDTO {
    private String title;
    private String description;
    private Double price;
    private int numberOfRooms;
    private int usefulSurface;
    private int constructionYear;
    private PostType type;
    private PropertyType propertyType;
}
