package com.example.demo.repository;

import com.example.demo.model.Post;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId); // Belirli bir kategoriye ait postları bulma
    List<Post> findByUserId(Long userId); // Belirli bir kullanıcıya ait postları bulma
}