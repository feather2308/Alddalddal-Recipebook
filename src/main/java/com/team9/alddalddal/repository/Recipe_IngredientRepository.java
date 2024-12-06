package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Ingredient;
import com.team9.alddalddal.entity.Recipe_Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Recipe_IngredientRepository extends JpaRepository<Recipe_Ingredient, Long> {
    List<Recipe_Ingredient> findByCocktail(Cocktail cocktail);
    List<Recipe_Ingredient> findByIngredient(Ingredient ingredient);
    List<Recipe_Ingredient> findByCocktailAndIngredient(Cocktail cocktail, Ingredient ingredient);
}
