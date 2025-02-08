package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show signup page
    @GetMapping("/signup")
    public String showSignupForm(@ModelAttribute("user") User user) {
        
        return "signup";
    }

    // Handle signup form submission
    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute User user, Model model) {
        userService.registerUser(user);  // Şifreyi encode edip kullanıcıyı kaydet
        return "redirect:/users/login";  // Kayıt işlemi tamamlandığında login sayfasına yönlendir
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.validateUserCredentials(username, password)) {
            return "redirect:/dashboard";  // Redirect to home page after successful login
        }
        model.addAttribute("error", "Invalid credentials.");
        return "login";  // Return to login page if credentials are incorrect
    }

    // Diğer API uç noktaları
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.findUserById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    return ResponseEntity.ok(userService.saveUser(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
