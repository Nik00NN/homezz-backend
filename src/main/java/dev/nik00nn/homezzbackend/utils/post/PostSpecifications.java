package dev.nik00nn.homezzbackend.utils.post;

import dev.nik00nn.homezzbackend.domain.PostType;
import dev.nik00nn.homezzbackend.domain.PropertyType;
import org.springframework.data.jpa.domain.Specification;
import dev.nik00nn.homezzbackend.domain.Post;

public class PostSpecifications {

    public static Specification<Post> hasPropertyType(PropertyType propertyType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("propertyType"), propertyType);
    }

    public static Specification<Post> hasPostType(PostType postType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), postType);
    }

    public static Specification<Post> hasNumberOfRooms(int numberOfRooms) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("numberOfRooms"), numberOfRooms);
    }

    public static Specification<Post> hasUsefulSurface(int usefulSurface) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("usefulSurface"), usefulSurface);
    }

    public static Specification<Post> hasConstructionYear(int constructionYear) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("constructionYear"), constructionYear);
    }
}

