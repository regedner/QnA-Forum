package com.example.demo.controller;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@Controller
public class DashboardController {

    @Autowired
    private PostService postService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "dashboard"; // Dashboard sayfasını döndür
    }

    @PostMapping("/posts/like/{id}")
    public String likePost(@PathVariable Long id, Model model) {
        Post post = postService.findPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));  // Optional<Post> artık Post objesine dönüştürüldü
        
        // Like sayısını 1 artırıyoruz
        post.setLikeCount(post.getLikeCount() + 1);
        
        // Güncellenmiş postu kaydediyoruz
        postService.savePost(post);
        
        // Like sayısını döndüren model'i ekle
        model.addAttribute("newLikeCount", post.getLikeCount());

        // Post listesi ve yeni like sayısını dashboard sayfasına ekle
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        
        return "dashboard"; // Dashboard sayfasını tekrar döndür
    }
}
