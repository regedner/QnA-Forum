package com.example.demo.service;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Like oluşturma ve like sayısını artırma
    public void likePost(Long postId, Long userId) {
        // Post ve User nesnelerini veritabanından al
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Kullanıcının aynı postu daha önce beğenip beğenmediğini kontrol et
        if (!likeRepository.existsByPostIdAndUserId(postId, userId)) {
            // Yeni bir Like nesnesi oluştur
            Like like = new Like(post, user);
            likeRepository.save(like);  // Like'ı veritabanına kaydet

            // Post'un like sayısını 1 artır
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);  // Post'u güncelle
        }
    }

    // Like sayısını döndüren metod
    public Long getLikeCountForPost(Long postId) {
        return likeRepository.countByPostId(postId);  // Post'a ait beğeni sayısını döndür
    }

    // Like silme
    public void deleteLike(Long postId, Long userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepository.delete(like);  // Like'ı sil
        Post post = like.getPost();
        post.setLikeCount(post.getLikeCount() - 1);  // Post'un like sayısını 1 azalt
        postRepository.save(post);  // Post'u güncelle
    }
}
