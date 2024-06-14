package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<PostDTO> allPosts = postService.getAll(page,size);
        return ResponseEntity.ok().body(allPosts);
    }
}
