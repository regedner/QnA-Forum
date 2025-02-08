package com.example.demo.service;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }
    public  Tag findOrCreateByName(String name) {
        return tagRepository.findByName(name)
                .orElseGet(() -> tagRepository.save(new Tag(name)));
    }
}
