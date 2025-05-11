package com.translation.assessment.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.translation.assessment.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
 Optional<Tag> findByName(String name);
}