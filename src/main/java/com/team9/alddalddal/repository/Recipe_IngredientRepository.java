package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Recipe_Ingredient;
import com.team9.alddalddal.entity.Recipe_IngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Recipe_IngredientRepository extends JpaRepository<Recipe_Ingredient, Long> {
    List<Recipe_Ingredient> findById_Cname(String cocktail_name);
    List<Recipe_Ingredient> findById_Iname(String ingredient_name);
    List<Recipe_Ingredient> findById(Recipe_IngredientId id);
}
