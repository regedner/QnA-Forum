package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

	
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;  // CategoryRepository'yi ekledik
    private final TagRepository tagRepository;  // TagRepository'yi ekledik

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // Yeni post kaydetme
    public Post savePost(Post post) {
        return postRepository.save(post);  // Veritabanına kaydeder
    }

    // Id'ye göre postu bulma
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);  // Veritabanında id'ye göre postu arar
    }
    
    public void likePost(Long id) {
    	Post post = findPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikeCount(post.getLikeCount() + 1);  // Like sayısını 1 artır
        postRepository.save(post);  // Değişiklikleri veritabanına kaydet
    }
    // Tüm postları getirme
    public List<Post> findAllPosts() {
    	List<Post> posts = postRepository.findAll();
        System.out.println("Total posts: " + posts.size());  // Konsola post sayısını yazdır
        return posts;  // Veritabanındaki tüm postları getirir
    }

    // Id ile post silme
    public void deletePostById(Long id) {
        postRepository.deleteById(id);  // Veritabanındaki id'ye göre postu siler
    }

    // Kategori Id'sine göre postları getirme
    public List<Post> findPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);  // Veritabanından kategoriye göre postları getirir
    }

    // Kullanıcı Id'sine göre postları getirme
    public List<Post> findPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);  // Veritabanından kullanıcıya göre postları getirir
    }
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();  // Tüm kategorileri getir
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();  // Tüm etiketleri getir
    }
}
