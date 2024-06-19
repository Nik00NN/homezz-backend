package dev.nik00nn.homezzbackend.repository;

import dev.nik00nn.homezzbackend.domain.Post;
import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import dev.nik00nn.homezzbackend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);
    @Query("SELECT p FROM Post p WHERE " +
            "(:propertyType IS NULL OR p.propertyType = :propertyType) AND " +
            "(:postType IS NULL OR p.type = :postType) AND " +
            "(:numberOfRooms IS NULL OR (p.numberOfRooms = :numberOfRooms OR (p.numberOfRooms >= 3 AND :numberOfRooms = 3))) AND " +
            "(:usefulSurface IS NULL OR " +
            "(p.usefulSurface <= 50 AND :usefulSurface = 50) OR " +
            "(p.usefulSurface > 50 AND p.usefulSurface <= 100 AND :usefulSurface = 100) OR " +
            "(p.usefulSurface > 100 AND p.usefulSurface <= 150 AND :usefulSurface = 150) OR " +
            "(p.usefulSurface > 150 AND :usefulSurface = 200)) AND " +
            "(:constructionYear IS NULL OR p.constructionYear = :constructionYear)")
    Page<Post> filterPosts(
            @Param("propertyType") PropertyType propertyType,
            @Param("postType") PostType postType,
            @Param("numberOfRooms") Integer numberOfRooms,
            @Param("usefulSurface") Integer usefulSurface,
            @Param("constructionYear") Integer constructionYear,
            Pageable pageable);
}
