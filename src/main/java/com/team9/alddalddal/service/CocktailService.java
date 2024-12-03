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

    public Page<Cocktail> getPageCocktails(int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return cocktailRepository.findAll(pageable);
    }

    public List<Cocktail> findCocktailsByNameContaining(String name){
        return cocktailRepository.findByNameContaining(name);
    }

    public List<Cocktail> findCocktailsByIngredient(String ingredient_name){
        List<Cocktail> cocktails = new ArrayList<>();
        List<Recipe_Ingredient> recipe_ingredients = recipe_ingredientRepository.findById_Iname(ingredient_name);
        for (Recipe_Ingredient recipe_ingredient : recipe_ingredients){
            Optional<Cocktail> cocktail = cocktailRepository.findByName(recipe_ingredient.getId().getCname());
            cocktail.ifPresent(cocktails::add);
        }
        return cocktails;
    }

    public List<Cocktail> findCocktailsByTrait(String trait){
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

    public List<Cocktail> findCocktailsByNameContainingAndIngredient(String cocktail_name, String ingredient_name){
        List<Cocktail> cocktails = new ArrayList<>();
        List<Recipe_Ingredient> recipe_ingredients = recipe_ingredientRepository.findById(new Recipe_IngredientId(cocktail_name, ingredient_name));
        for (Recipe_Ingredient recipe_ingredient : recipe_ingredients){
            Optional<Cocktail> cocktail = cocktailRepository.findByName(recipe_ingredient.getId().getCname());
            cocktail.ifPresent(cocktails::add);
        }
        return cocktails;
    }

    public List<Cocktail> findCocktailsByNameContainingAndTrait(String cocktail_name, String trait){
        List<Cocktail> cocktails = new ArrayList<>();
        Optional<Tag> tag = tagRepository.findByTrait(trait);
        if (tag.isPresent()) {
            int tagId = tag.get().getId();
            List<Cocktail_Tag> cocktailTags = cocktail_tagRepository.findById_Tag(tagId);
            for (Cocktail_Tag cocktailTag : cocktailTags) {
                Optional<Cocktail> cocktail = cocktailRepository.findByName(cocktailTag.getId().getName());
                cocktail.ifPresent(c -> {
                    if (c.getName().contains(cocktail_name)) {
                        cocktails.add(c);
                    }
                });
            }
        }
        return cocktails;
    }

    public List<Cocktail> findCocktailsByIngredientAndTrait(String ingredient_name, String trait){
        List<Cocktail> cocktails = new ArrayList<>();
        Optional<Tag> tag = tagRepository.findByTrait(trait);
        if (tag.isPresent()) {
            int tagId = tag.get().getId();
            List<Cocktail_Tag> cocktailTags = cocktail_tagRepository.findById_Tag(tagId);
            for (Cocktail_Tag cocktailTag : cocktailTags) {
                String cocktailName = cocktailTag.getId().getName();
                List<Recipe_Ingredient> recipeIngredients = recipe_ingredientRepository.findById_Cname(cocktailName);
                for (Recipe_Ingredient recipeIngredient : recipeIngredients) {
                    if (recipeIngredient.getId().getIname().equals(ingredient_name)) {
                        Optional<Cocktail> cocktail = cocktailRepository.findByName(cocktailName);
                        cocktail.ifPresent(cocktails::add);
                    }
                }
            }
        }
        return cocktails;
    }

    public List<Cocktail> findCocktailsByMixedCriteria(String cocktail_name, String ingredient_name, String trait){
        List<Cocktail> cocktails = new ArrayList<>();
        Optional<Tag> tag = tagRepository.findByTrait(trait);
        if (tag.isPresent()) {
            int tagId = tag.get().getId();
            List<Cocktail_Tag> cocktailTags = cocktail_tagRepository.findById_Tag(tagId);
            for (Cocktail_Tag cocktailTag : cocktailTags) {
                String cocktailName = cocktailTag.getId().getName();
                List<Recipe_Ingredient> recipeIngredients = recipe_ingredientRepository.findById_Cname(cocktailName);
                for (Recipe_Ingredient recipeIngredient : recipeIngredients) {
                    if (recipeIngredient.getId().getIname().equals(ingredient_name) && cocktailName.contains(cocktail_name)) {
                        Optional<Cocktail> cocktail = cocktailRepository.findByName(cocktailName);
                        cocktail.ifPresent(cocktails::add);
                    }
                }
            }
        }
        return cocktails;
    }

    public List<Ingredient> getListIngredients(){
        return ingredientRepository.findAll();
    }

    public Cocktail getCocktailByName(String name){
        return cocktailRepository.findByName(name).isPresent() ? cocktailRepository.findByName(name).get() : null;
    }

    public Recipe getRecipeByName(String name){
        return recipeRepository.findByName(name).isPresent() ? recipeRepository.findByName(name).get() : null;
    }

    public List<Recipe_Ingredient> getRecipeIngredientByCocktailName(String name){
        return recipe_ingredientRepository.findById_Cname(name);
    }

    public Tag getTagById(int id){
        return tagRepository.findById(id).isPresent() ? tagRepository.findById(id).get() : null;
    }

    public List<Tag> findTagsByName(String name) {
        List<Cocktail_Tag> tags_id = cocktail_tagRepository.findById_Name(name);
        List<Tag> tags = new ArrayList<>();
        for (Cocktail_Tag cocktail_tag : tags_id) {
            Optional<Tag> tag = tagRepository.findById(cocktail_tag.getId().getTag());
            tag.ifPresent(tags::add);
        }
        return tags;
    }

    public List<String> findByIngredientsNameContaining(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameContaining(name);
        List<String> ingredientsNames = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientsNames.add(ingredient.getName());
        }
        return ingredientsNames;
    }
}
