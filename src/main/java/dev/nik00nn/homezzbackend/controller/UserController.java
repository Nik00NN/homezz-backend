package dev.nik00nn.homezzbackend.controller;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.dto.*;
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
        String profilePhoto = userService.getProfilePhoto(username);
        return ResponseEntity.ok().body(profilePhoto);
    }

    @PostMapping(value = "/{username}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createPost(@PathVariable String username, @RequestPart("request") CreatePostDTO postRequest, @RequestParam("postPhotos") List<MultipartFile> files) throws IOException {
        UserDTO userAfterAddPost = userService.addPost(username, postRequest, files);
        if (userAfterAddPost != null) {
            return ResponseEntity.ok().body(userAfterAddPost);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username) {
        UserProfileDTO userProfile = userService.getUserProfile(username);
        return ResponseEntity.ok().body(userProfile);
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable String username) {
        List<PostDTO> posts = userService.getPosts(username);
        return ResponseEntity.ok().body(posts);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        userService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/favoritePosts/{postId}")
    public ResponseEntity<Boolean> isFavoritePost(@PathVariable String username, @PathVariable Long postId) {
        Boolean isPostFavorite = userService.isFavorite(username, postId);
        return ResponseEntity.ok().body(isPostFavorite);
    }

    @PostMapping("/{username}/add-favorite/{postId}")
    public ResponseEntity<String> addFavoritePost(@PathVariable String username, @PathVariable Long postId) {
        userService.addPostToFavorite(username, postId);
        return ResponseEntity.ok().body("ad fav succes");
    }

    @DeleteMapping("/{username}/remove-favorite/{postId}")
    public ResponseEntity<String> removeFavoritePost(@PathVariable String username, @PathVariable Long postId){
        userService.deletePostFromFavorite(username,postId);
        return ResponseEntity.ok().body("delete fav succes");
    }

    @GetMapping("/{username}/favoritesPosts")
    public ResponseEntity<List<PostDTO>> getFavoritePosts(@PathVariable String username) {
        List<PostDTO> allFavoritePosts = userService.getAllFavoritePosts(username);
        return ResponseEntity.ok().body(allFavoritePosts);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO request) {
        userService.update(request);
        return ResponseEntity.noContent().build();
    }

}
