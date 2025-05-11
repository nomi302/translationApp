package com.translation.assessment.service;

import org.springframework.stereotype.Service;

import com.translation.assessment.entity.Tag;
import com.translation.assessment.repository.TagRepository;


@Service
public class TagService {

    private final TagRepository tagRepository;
    

    public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public Tag findOrCreate(String tagName) {
        return tagRepository.findByName(tagName.toLowerCase())
            .orElseGet(() -> createTag(tagName));
    }

    private Tag createTag(String name) {
        return tagRepository.save(Tag.builder()
            .name(name.toLowerCase())
            .build());
    }
}
