package com.example.demo.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UserService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebMvcAutoConfiguration {

	
	
    // HttpSecurity yapılandırması
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF korumasını devre dışı bırakma
            .authorizeHttpRequests()
                .requestMatchers("/users/signup", "/users/login", "/dashboard", "/createpost", "/posts/create", "/posts/like", "/like").permitAll()  // Bu sayfalara erişim izni ver
                .anyRequest().authenticated()  // Diğer sayfalara erişim için kimlik doğrulama gerekiyor
            .and()
            .formLogin()
                .loginPage("/users/login")  // Özel login sayfasını belirleyin
                .loginProcessingUrl("/users/login")  // Giriş işlemi için URL
                .defaultSuccessUrl("/dashboard", true)  // Başarılı girişte yönlendirme
                .failureUrl("/users/login?error=true")  // Başarısız girişte yönlendirme
            .and()
            .logout()
                .logoutUrl("/logout")  // Çıkış URL'si
                .logoutSuccessUrl("/users/login");  // Çıkış başarılı olursa yönlendirme
        return http.build();
    }

    // PasswordEncoder bean'i oluşturuluyor
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Şifreyi güvenli bir şekilde encode etmek için BCrypt kullanıyoruz
    }

    // UserDetailsService bean'i oluşturuluyor
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            // UserService ile kullanıcıyı veritabanından buluyoruz
            com.example.demo.model.User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            // Spring Security için User objesini dönüyoruz
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()  // Bu örnekte herhangi bir role sahip değil, ancak burada rol veya izinler eklenebilir
            );
        };
    }
}
