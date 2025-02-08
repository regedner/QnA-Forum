package com.example.demo.controller;


import com.example.demo.config.UserContextManager;
import com.example.demo.dto.PostDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Like;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.TagService;
import com.example.demo.service.UserService;

import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

	private final UserContextManager userContextManager;
    private final PostService postService;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;
    private final CategoryService categoryService;
    private final CommentService commentService;
    
    @Autowired
    public PostController(PostService postService,TagRepository tagRepository,UserService userService, LikeRepository likeRepository,
    		UserContextManager userContextManager, CategoryService categoryService, CommentService commentService) {
        this.postService = postService;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
        this.categoryService = categoryService;
        this.userContextManager = userContextManager;
        this.commentService=commentService;
        
    }
   

    // Form sayfasına yönlendiren method
    @GetMapping("/createpost")
    public String showCreatePostForm(Model model) {
    	List<Category> categories = categoryService.findAllCategories(); 
        model.addAttribute("post", new Post());
        model.addAttribute("category", new Category()); 
        // Kategoriler ve etiketler (eğer varsa) eklemek için örnek
        model.addAttribute("categories", postService.getAllCategories());
        model.addAttribute("tags", postService.getAllTags());
        return "createpost"; // Thymeleaf template adı
    }

    // Post oluşturma işlemi
    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post,
    		@RequestParam("category.id") Long categoryId,
    		Model model) {
    	
    	User loggedInUser = userContextManager.getCurrentUser(userService);
    	
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        category = categoryService.saveCategory(category);
        post.setUser(loggedInUser); 
        post.setCategory(category);
        postService.savePost(post);
        model.addAttribute("message", "Post created successfully!");
        return "redirect:/dashboard"; // Başarıyla oluşturulursa tekrar create sayfasına yönlendir
    }

    // Tüm postları listeleme
    @GetMapping
    public String getAllPosts(Model model) {
    	List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "dashboard"; // "dashboard.html" sayfasına yönlendirme
    }

    // Post güncelleme işlemi
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Optional<Post> postOptional = postService.findPostById(id);  // Optional döndürülüyor
        if (postOptional.isPresent()) {  // Optional içeriği varsa
            Post post = postOptional.get();  // Post'u al
            model.addAttribute("post", post);
            model.addAttribute("categories", postService.getAllCategories());
            model.addAttribute("tags", postService.getAllTags());
            return "editPost";  // Post güncelleme sayfasına yönlendir
        }
        return "redirect:/posts";  // Eğer post bulunamazsa, tüm postlara yönlendir
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post updatedPost, Model model) {
        Optional<Post> postOptional = postService.findPostById(id);  // Optional döndürülüyor
        if (postOptional.isPresent()) {  // Eğer post varsa
            Post existingPost = postOptional.get();  // Post'u al
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            existingPost.setCategory(updatedPost.getCategory());
            postService.savePost(existingPost);  // Post'u güncelle
            model.addAttribute("message", "Post updated successfully!");
        }
        return "redirect:/posts";  // Güncellenen post sonrası dashboard'a yönlendir
    }

    @Transactional
    @PostMapping("/like/{postId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likePost(@PathVariable Long postId) {
        // Giriş yapan kullanıcıyı bul
    	User loggedInUser = userContextManager.getCurrentUser(userService);

        // Post'u bul
        Post post = postService.findPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Kullanıcı daha önce like atmış mı kontrol et
      

            // Like sayısını artır
            post.setLikeCount(post.getLikeCount() + 1);
            postService.savePost(post);  // Post'u güncelle
        

        // Yeni like sayısını döndür
        Map<String, Object> response = new HashMap<>();
        response.put("newLikeCount", post.getLikeCount());  // `newLikeCount`'u JSON formatında gönderiyoruz

        return ResponseEntity.ok(response);  // JSON döndür
    }

    
    // Post silme işlemi
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        postService.deletePostById(id);
        model.addAttribute("message", "Post deleted successfully!");
        return "redirect:/posts"; // Silme sonrası dashboard'a yönlendirme
    }
    @GetMapping("/posts/createpost")
    public String createPostForm(Model model) {
        // Kategorileri ve etiketleri hazırlayın
        List<String> categories = List.of("Te", "Health", "Education");
        List<String> tags = List.of("Java", "Spring", "Thymeleaf", "Programming");

        // Model'e ekleyin
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tags);

        // Post nesnesini de model'e ekleyebilirsiniz
        model.addAttribute("post", new Post());

        return "createpost"; // Thymeleaf template dosyanızın adı
    }
 // Belirli bir posta yorum ekleme
    @GetMapping("/posts/comment/{postId}")
    public String getCommentPage(@PathVariable Long id, Model model) {
        model.addAttribute("commentId", id); // Model'e ID'yi ekliyoruz
        return "comments"; // Thymeleaf "comments.html" sayfasını döndürecek
    }
    
    
    @PostMapping("/comment/{postId}")
    public String addCommentToPost(@PathVariable Long postId, @RequestParam String content, Model model) {
        User loggedInUser = userContextManager.getCurrentUser(userService); // Mevcut kullanıcıyı al

        // İlgili post'u bul
        Post post = postService.findPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Yeni yorum oluştur ve kaydet
        Comment comment = new Comment(content, post, loggedInUser);
        commentService.saveComment(comment);

        // Dashboard'a yönlendir
        return "redirect:/posts";
    }


    // Belirli bir post'un yorumlarını listeleme
    @GetMapping("/{postId}/comments")
    public String getCommentsForPost(@PathVariable Long postId, Model model) {
        List<Comment> comments = commentService.findCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "comments"; // "comments.html" sayfasına yönlendirme
    }
   
}
