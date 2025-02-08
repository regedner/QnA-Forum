package com.example.demo.repository;

import com.example.demo.model.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	 Optional<Tag> findByName(String name);
    // İhtiyaç halinde özel sorgular eklenebilir
}