package dev.nik00nn.homezzbackend.service.user;


import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.ProfilePhoto;
import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.*;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IPostRepository;
import dev.nik00nn.homezzbackend.repository.IProfilePhotoRepository;
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
    private final IProfilePhotoRepository profilePhotoRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    public UserService(IUserRepository userRepository, IPostRepository postRepository, IProfilePhotoRepository profilePhotoRepository, ModelMapper modelMapper, FileService fileService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.profilePhotoRepository = profilePhotoRepository;
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
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    @Override
    public UserDTO addPost(String username, CreatePostDTO createPostRequest, List<MultipartFile> photos) throws IOException {
        User userForPost = userRepository.findByUsername(username).orElse(null);
        List<Post> posts;
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

    @Override
    public void addPostToFavorite(String username, Long postId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Post> postOptional = postRepository.findById(postId);
        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();
            user.getFavoritePosts().add(post);
            userRepository.save(user);
        }
    }

    @Override
    public List<PostDTO> getAllFavoritePosts(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        List<PostDTO> postDTOs = new ArrayList<>();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> posts = user.getFavoritePosts();
            for (Post post : posts) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postDTO.setUsername(post.getUser().getUsername());
                postDTOs.add(postDTO);
            }
        }
        return postDTOs;
    }

    @Override
    public Boolean isFavorite(String username, Long postId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Post> postOptional = postRepository.findById(postId);
        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();
            return user.getFavoritePosts().contains(post);
        }
        return false;
    }

    @Override
    public void deletePostFromFavorite(String username, Long postId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Post> postOptional = postRepository.findById(postId);
        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();
            user.getFavoritePosts().remove(post);
            userRepository.save(user);
        }
    }

    @Override
    public void update(String username, UserProfileDetailsDTO profileDetails) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmailAddress(profileDetails.getEmailAddress());
            user.setAddress(profileDetails.getAddress());
            user.setPhoneNumber(profileDetails.getPhoneNumber());
            userRepository.save(user);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmailAddress(email);
        return userOptional.orElse(null);
    }

    @Override
    public void updateProfilePicture(String username, MultipartFile profilePicture) throws IOException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ProfilePhoto newProfilePhoto = fileService.saveProfilePhoto(profilePicture);
            ProfilePhoto oldProfilePhoto = user.getProfilePicture();

            user.setProfilePicture(null);
            profilePhotoRepository.deleteById(oldProfilePhoto.getId());

            if (newProfilePhoto != null) {
                profilePhotoRepository.save(newProfilePhoto);
                user.setProfilePicture(newProfilePhoto);
                userRepository.save(user);
            }
        }
    }

    @Override
    public Post updatePost(Long postId, PostDetailsDTO postDetailsDTO, List<MultipartFile> files) throws IOException {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setDescription(postDetailsDTO.getDescription());
            post.setPrice(postDetailsDTO.getPrice());
            post.setType(postDetailsDTO.getType());
            post.setTitle(postDetailsDTO.getTitle());
            post.setConstructionYear(postDetailsDTO.getConstructionYear());
            post.setNumberOfRooms(postDetailsDTO.getNumberOfRooms());
            post.setPropertyType(postDetailsDTO.getPropertyType());
            post.setUsefulSurface(postDetailsDTO.getUsefulSurface());

            List<File> photos = post.getPhotos();
            if (files != null) {
                for (MultipartFile file : files) {
                    photos.add(fileService.saveImage(file));
                }
            }

            post.setPhotos(photos);

            return postRepository.save(post);
        }
        return null;
    }
}
