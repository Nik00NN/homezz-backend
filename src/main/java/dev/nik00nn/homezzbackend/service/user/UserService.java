package dev.nik00nn.homezzbackend.service.user;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.*;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IPostRepository;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import dev.nik00nn.homezzbackend.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    public UserService(IUserRepository userRepository, IPostRepository postRepository, ModelMapper modelMapper, FileService fileService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return modelMapper.map(userRepository.findByUsername(username).get(), UserDTO.class);
        }
        return null;
    }

    @Override
    public UserDTO addPost(String username, CreatePostDTO createPostRequest, List<MultipartFile> photos) throws IOException {
        User userForPost = userRepository.findByUsername(username).orElse(null);
        List<Post> posts = new ArrayList<>();
        if (userForPost == null) {
            return null;
        } else {
            posts = userForPost.getPosts();
        }


        Post postToCreate = modelMapper.map(createPostRequest, Post.class);
        postToCreate.setUser(userForPost);
        postToCreate.setCreationDate(LocalDate.now());


        List<File> postPhotos = new ArrayList<>();
        for (MultipartFile photo : photos) {
            postPhotos.add(fileService.saveImage(photo));
        }
        postToCreate.setPhotos(postPhotos);
        Post postCreated = postRepository.save(postToCreate);
        posts.add(postCreated);
        userForPost.setPosts(posts);


        return modelMapper.map(userForPost, UserDTO.class);
    }

    @Override
    public String getProfilePhoto(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            byte[] imageContent = user.getProfilePicture().getFileContent();
            return Base64.getEncoder().encodeToString(imageContent);
        }
        return null;
    }

    @Override
    public UserProfileDTO getUserProfile(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return modelMapper.map(userRepository.findByUsername(username).get(), UserProfileDTO.class);
        }
        return null;
    }

    @Override
    public List<PostDTO> getPosts(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        List<PostDTO> postDTOs = new ArrayList<>();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> posts = user.getPosts();
            for (Post post : posts) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postDTOs.add(postDTO);
            }
            return postDTOs;
        }
        return new ArrayList<>();
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }


}
