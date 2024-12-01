package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByName(String name);
}
