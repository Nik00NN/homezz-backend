package dev.nik00nn.homezzbackend.service.user;

import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getCurrentUser();

    User getUserByUsername(String username);

    UserDTO addPost(String username, CreatePostDTO createPostRequest, List<MultipartFile> photos) throws IOException;

    String getProfilePhoto(String username);

    UserProfileDTO getUserProfile(String username);

    List<PostDTO> getPosts(String username);

    void deletePost(Long postId);

    void addPostToFavorite(String username, Long postId);

    List<PostDTO> getAllFavoritePosts(String username);

    Boolean isFavorite(String username, Long postId);

    void deletePostFromFavorite(String username, Long postId);

    void update(String username, UserProfileDetailsDTO profileDetails);

    User getUserByEmail(String email);

    void updateProfilePicture(String username, MultipartFile profilePicture) throws IOException;

    Post updatePost(Long postId, PostDetailsDTO postDetailsDTO, List<MultipartFile> files) throws IOException;

    void changePassword(String mail, String newPassword);
}

