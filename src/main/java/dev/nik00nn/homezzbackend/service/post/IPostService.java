package dev.nik00nn.homezzbackend.service.post;

import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import dev.nik00nn.homezzbackend.dto.PostDTO;

import java.util.List;

public interface IPostService {
    List<PostDTO> getAll(PropertyType propertyType, PostType postType, Integer numberOfRooms, Integer usefulSurface, Integer constructionYear, int page, int size);
    PostDTO getById(Long id);

}
