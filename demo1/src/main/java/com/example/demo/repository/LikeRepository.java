package com.example.demo.repository;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Post ve kullanıcıya göre daha önce beğeni olup olmadığını kontrol etme
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
    boolean existsByPostAndUser(Post post, User user);
    Long countByPostId(Long postId);
}
