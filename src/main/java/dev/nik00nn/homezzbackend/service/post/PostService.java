package dev.nik00nn.homezzbackend.service.post;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.dto.create.CreatePostDTO;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IPostRepository;
import dev.nik00nn.homezzbackend.service.file.FileService;
import dev.nik00nn.homezzbackend.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService{

    private final IPostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final FileService fileService;

    public PostService(IPostRepository postRepository, ModelMapper modelMapper, UserService userService, FileService fileService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public PostDTO createPost(CreatePostDTO createPostRequest, List<MultipartFile> files) throws IOException {
        if(files.size() > 3) {
            throw new BadRequestException("You can add 3 pictures maximum");
        }

        User currentUser = userService.getCurrentUser().get();
        List<Post> currentUserPosts = currentUser.getPosts();

        Post postToCreate = modelMapper.map(createPostRequest, Post.class);
        postToCreate.setCreationDate(LocalDate.now());
        List<File> photos = new ArrayList<>();
        for(MultipartFile file : files) {
            File photo = fileService.checkAndSaveImage(file);
            photos.add(photo);
        }
        postToCreate.setPhotos(photos);
        currentUserPosts.add(postToCreate);
        currentUser.setPosts(currentUserPosts);
        postRepository.save(postToCreate);

        return modelMapper.map(postToCreate,PostDTO.class);
    }

}
