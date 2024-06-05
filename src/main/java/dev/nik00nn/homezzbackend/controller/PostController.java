package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.dto.create.CreatePostDTO;
import dev.nik00nn.homezzbackend.service.post.PostService;
import dev.nik00nn.homezzbackend.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestPart CreatePostDTO request,@RequestParam("photos") List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(postService.createPost(request,files));
    }

}
