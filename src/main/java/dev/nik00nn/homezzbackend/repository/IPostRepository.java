package dev.nik00nn.homezzbackend.repository;

import dev.nik00nn.homezzbackend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);
}
