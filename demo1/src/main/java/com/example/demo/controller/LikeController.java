package com.example.demo.controller;

import com.example.demo.service.LikeService;
import com.example.demo.service.UserService;
import com.example.demo.config.UserContextManager;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final UserContextManager userContextManager;  // UserContextManager ekledik

    @Autowired
    public LikeController(LikeService likeService, UserService userService, UserContextManager userContextManager) {
        this.likeService = likeService;
        this.userService = userService;
        this.userContextManager = userContextManager;  // Constructor'a ekledik
    }

    // Like işlemi: Kullanıcı bir postu beğendiğinde
    @PostMapping("/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        // Giriş yapan kullanıcıyı al
        User loggedInUser = userContextManager.getCurrentUser(userService);  // Oturum açan kullanıcıyı al
        
        likeService.likePost(postId, loggedInUser.getId());  // Like işlemi
        return ResponseEntity.ok("Post liked successfully!");
    }

    // Belirli bir postun like sayısını getirme
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        Long likeCount = likeService.getLikeCountForPost(postId);
        return ResponseEntity.ok(likeCount);  // Beğeni sayısını döndür
    }

    // Like silme işlemi
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long postId) {
        // Giriş yapan kullanıcıyı al
        User loggedInUser = userContextManager.getCurrentUser(userService);  // Oturum açan kullanıcıyı al
        
        likeService.deleteLike(postId, loggedInUser.getId());  // Like'ı sil
        return ResponseEntity.ok("Post unliked successfully!");
    }
}
