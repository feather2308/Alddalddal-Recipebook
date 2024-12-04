package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByName(String name);

    void deleteById(int id);
}
