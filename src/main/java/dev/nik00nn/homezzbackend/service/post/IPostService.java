package dev.nik00nn.homezzbackend.service.post;

import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.dto.create.CreatePostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    PostDTO createPost(CreatePostDTO createPostRequest, List<MultipartFile> files) throws IOException;
}
