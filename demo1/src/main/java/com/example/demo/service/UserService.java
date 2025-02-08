package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Kullanıcıyı kaydeder.
     * @param user Kaydedilecek kullanıcı.
     * @return Kaydedilen kullanıcı.
     */
    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Şifreyi encode et
        return userRepository.save(user);
    }

    /**
     * Kullanıcı adı ile kullanıcıyı bulur.
     * @param username Kullanıcı adı.
     * @return Kullanıcı (varsa).
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * E-posta adresi ile kullanıcıyı bulur.
     * @param email E-posta adresi.
     * @return Kullanıcı (varsa).
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * ID ile kullanıcıyı bulur.
     * @param id Kullanıcı ID.
     * @return Kullanıcı (varsa).
     */
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Tüm kullanıcıları döndürür.
     * @return Kullanıcı listesi.
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Kullanıcıyı siler.
     * @param id Silinecek kullanıcının ID'si.
     */
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Kullanıcı giriş bilgilerini doğrular.
     * @param username Kullanıcı adı.
     * @param password Şifre.
     * @return Giriş bilgileri doğru mu.
     */
    public boolean validateUserCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    /**
     * Yeni bir kullanıcı kaydeder.
     * @param user Kaydedilecek kullanıcı.
     */
    @Transactional
    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Şifreyi encode et
        userRepository.save(user); // Kullanıcıyı kaydet
    }
}
