package dev.nik00nn.homezzbackend.controller;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.dto.CreatePostDTO;
import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.service.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/files")
    public ResponseEntity<String> getProfilePhoto(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        byte[] imageContent = user.getProfilePicture().getFileContent();
        String base64Image = Base64.getEncoder().encodeToString(imageContent);
        return ResponseEntity.ok().body(base64Image);
    }

    @PostMapping(value = "/{username}/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createPost(@PathVariable String username, @RequestPart("request") CreatePostDTO postRequest, @RequestParam("postPhotos")List<MultipartFile> files) throws IOException {
        UserDTO userAfterAddPost = userService.addPost(username, postRequest,files);
        return ResponseEntity.ok().body(userAfterAddPost);
    }
}
