package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<PostDTO> getAllPosts(
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) PostType postType,
            @RequestParam(required = false) Integer numberOfRooms,
            @RequestParam(required = false) Integer usefulSurface,
            @RequestParam(required = false) Integer constructionYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return postService.getAll(propertyType, postType, numberOfRooms, usefulSurface, constructionYear, page, size);
    }

    @GetMapping("/{postId}/files")
    public ResponseEntity<List<String>> getPostPhotos(@PathVariable Long postId){
        PostDTO post = postService.getById(postId);
        List<String> photos = new ArrayList<>();

        for(File file : post.getPhotos()){
            byte[] imageContent = file.getFileContent();
            String base64Image = Base64.getEncoder().encodeToString(imageContent);
            photos.add(base64Image);
        }

        return ResponseEntity.ok().body(photos);
    }
}
