package dev.nik00nn.homezzbackend.service.post;


import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.dto.PostDTO;
import dev.nik00nn.homezzbackend.repository.IPostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService{

    private final IPostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(IPostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> pagedResult = postRepository.findAll(pageable);
        List<PostDTO> allPostsDTO = new ArrayList<>();

        for(Post post : pagedResult.getContent()){
            allPostsDTO.add(modelMapper.map(post, PostDTO.class));
        }

        return allPostsDTO;
    }
}
