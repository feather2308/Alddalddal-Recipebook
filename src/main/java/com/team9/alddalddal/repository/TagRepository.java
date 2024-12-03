package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findById(int id);
    Optional<Tag> findByTrait(String trait);
}
