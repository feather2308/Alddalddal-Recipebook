package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.*;
import com.team9.alddalddal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    public Page<Cocktail> listCocktails(int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return cocktailRepository.findAll(pageable);
    }

    public List<Ingredient> listIngredients(){
        return ingredientRepository.findAll();
    }

    public Cocktail getCocktailByName(String name){
        return cocktailRepository.findByName(name);
    }

    public Recipe getRecipeByName(String name){
        return recipeRepository.findByName(name);
    }
}
