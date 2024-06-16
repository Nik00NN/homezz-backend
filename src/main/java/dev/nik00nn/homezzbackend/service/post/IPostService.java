package dev.nik00nn.homezzbackend.service.post;

import dev.nik00nn.homezzbackend.dto.PostDTO;

import java.util.List;

public interface IPostService {
    List<PostDTO> getAll(int page,int size);
    PostDTO getById(Long id);

}
