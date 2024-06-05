package dev.nik00nn.homezzbackend.dto.create;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private Double price;
    @NotEmpty
    private int numberOfRooms;
    @NotEmpty
    private int usefulSurface;
    @NotEmpty
    private int constructionYear;
    @NotEmpty
    private PostType type;
    @NotEmpty
    private PropertyType propertyType;
}
