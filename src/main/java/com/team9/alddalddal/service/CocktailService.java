package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.*;
import com.team9.alddalddal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private Recipe_IngredientRepository recipe_ingredientRepository;
    @Autowired
    private Cocktail_TagRepository cocktail_tagRepository;
    @Autowired
    private TagRepository tagRepository;

    public Page<Cocktail> listCocktails(int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return cocktailRepository.findAll(pageable);
    }

    public List<Ingredient> listIngredients(){
        return ingredientRepository.findAll();
    }

    public Cocktail getCocktailByName(String name){
        return cocktailRepository.findByName(name).isPresent() ? cocktailRepository.findByName(name).get() : null;
    }

    public Recipe getRecipeByName(String name){
        return recipeRepository.findByName(name);
    }

    public List<Recipe_Ingredient> getRecipeIngredientByCocktailName(String name){
        return recipe_ingredientRepository.findById_Cname(name);
    }

    public List<Recipe_Ingredient> getRecipeIngredientByIngredientName(String name){
        return recipe_ingredientRepository.findById_Iname(name);
    }

    public Optional<Tag> getTagById(int id){
        return tagRepository.findById(id);
    }

    public List<Tag> getTags(String name){
        List<Cocktail_Tag> tags_id = cocktail_tagRepository.findById_Name(name);
        List<Tag> tags = new ArrayList<>();
        for (Cocktail_Tag cocktail_tag : tags_id) {
            Optional<Tag> tag = tagRepository.findById(cocktail_tag.getId().getTag());
            tag.ifPresent(tags::add);
        }
        return tags;
    }

    public List<Cocktail> getCocktailsByTrait(String trait){
        List<Cocktail> cocktails = new ArrayList<>();
        Optional<Tag> tag = tagRepository.findByTrait(trait);

        if(tag.isPresent()){
            int id = tag.get().getId();
            List<Cocktail_Tag> cocktail_tags = cocktail_tagRepository.findById_Tag(id);
            for (Cocktail_Tag cocktail_tag : cocktail_tags) {
                Optional<Cocktail> cocktail = cocktailRepository.findByName(cocktail_tag.getId().getName());
                cocktail.ifPresent(cocktails::add);
            }
        }
        return cocktails;
    }
}
