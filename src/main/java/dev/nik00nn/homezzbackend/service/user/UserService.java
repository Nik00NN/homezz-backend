package dev.nik00nn.homezzbackend.service.user;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.CreatePostDTO;
import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IPostRepository;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import dev.nik00nn.homezzbackend.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        Optional<User> userOptional = userRepository.findByUsername(username);
        return modelMapper.map(userOptional.orElse(null), UserDTO.class);
    }

    @Override
    public UserDTO addPost(String username, CreatePostDTO createPostRequest, List<MultipartFile> photos) throws IOException {
        User userForPost = userRepository.findByUsername(username).orElse(null);
        List<Post> posts = userForPost.getPosts();


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

}
