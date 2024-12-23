package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.*;
import com.team9.alddalddal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    public Page<Cocktail> getPageCocktails(int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return cocktailRepository.findAll(pageable);
    }

    public List<Cocktail> findCocktailsByNameContaining(String cocktail_name){
        return cocktailRepository.findByNameContainingIgnoreCase(cocktail_name);
    }

    public List<String> findIngredientsNameByNameContaining(String ingredient_name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameContaining(ingredient_name);
        List<String> ingredientsNames = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientsNames.add(ingredient.getName());
        }
        return ingredientsNames;
    }

    public List<String> findTraitsNameByNameContaining(String trait_name) {
        List<Tag> tags = tagRepository.findByTraitContaining(trait_name);
        List<String> traitNames = new ArrayList<>();
        for (Tag tag : tags) {
            traitNames.add(tag.getTrait());
        }
        return traitNames;
    }

    public List<Cocktail> findCocktailsByIngredientNameContaining(Object object){
        if (object instanceof String) {
            return findCocktailsByIngredientNameContaining((String) object);
        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) object;
            return findCocktailsByIngredientNameContaining(list);
        } else {
            return null;
        }
    }

    public List<Cocktail> findCocktailsByIngredientNameContaining(String ingredient_name){
        Set<Cocktail> cocktailSet = new HashSet<>();
        for (Ingredient ingredient : ingredientRepository.findByNameContaining(ingredient_name)) {
            for (Recipe_Ingredient recipeIngredient : recipe_ingredientRepository.findByIngredient(ingredient)) {
                cocktailSet.add(recipeIngredient.getCocktail());
            }
        }
        return new ArrayList<>(cocktailSet);
    }

    public List<Cocktail> findCocktailsByIngredientNameContaining(List<String> ingredient_names){
        List<Cocktail> cocktails = cocktailRepository.findAll();
        List<Cocktail> cocktails_;

        for (String ingredient_name : ingredient_names) {
            cocktails_ = new ArrayList<>();
            for (Ingredient ingredient : ingredientRepository.findByNameContaining(ingredient_name)) {
                for (Recipe_Ingredient recipeIngredient : recipe_ingredientRepository.findByIngredient(ingredient)) {
                    cocktails_.add(recipeIngredient.getCocktail());
                }
            }
            cocktails = findIntersection(cocktails, cocktails_);
        }

        return cocktails;
    }

    public List<Cocktail> findCocktailsByTraitNameContaining(Object object){
        if (object instanceof String) {
            return findCocktailsByTraitNameContaining((String) object);
        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) object;
            return findCocktailsByTraitNameContaining(list);
        } else {
            return null;
        }
    }

    public List<Cocktail> findCocktailsByTraitNameContaining(String trait){
        Set<Cocktail> cocktails = new HashSet<>();
        for (Tag tag : tagRepository.findByTraitContaining(trait)){
            for(Cocktail_Tag cocktailTag : cocktail_tagRepository.findByTag(tag)){
                cocktails.add((cocktailTag.getCocktail()));
            }
        }
        return new ArrayList<>(cocktails);
    }

    public List<Cocktail> findCocktailsByTraitNameContaining(List<String> trait_names){
        List<Cocktail> cocktails = cocktailRepository.findAll();
        List<Cocktail> cocktails_;

        for (String trait_name : trait_names) {
            cocktails_ = new ArrayList<>();
            for (Tag tag : tagRepository.findByTraitContaining(trait_name)) {
                for (Cocktail_Tag cocktailTag : cocktail_tagRepository.findByTag(tag)) {
                    cocktails_.add(cocktailTag.getCocktail());
                }
            }
            cocktails = findIntersection(cocktails, cocktails_);
        }

        return cocktails;
    }

    public List<Cocktail> findCocktailsByNameContainingAndIngredient(String cocktail_name, Object ingredient_name){
        return findIntersection(findCocktailsByNameContaining(cocktail_name), findCocktailsByIngredientNameContaining(ingredient_name));
    }

    public List<Cocktail> findCocktailsByNameContainingAndTrait(String cocktail_name, Object trait){
        return findIntersection(findCocktailsByNameContaining(cocktail_name), findCocktailsByTraitNameContaining(trait));
    }

    public List<Cocktail> findCocktailsByIngredientAndTrait(Object ingredient_name, Object trait){
        return findIntersection(findCocktailsByIngredientNameContaining(ingredient_name), findCocktailsByTraitNameContaining(trait));
    }

    public List<Cocktail> findCocktailsByMixedCriteria(String cocktail_name, Object ingredient_name, Object trait){
        return findIntersection(findCocktailsByNameContainingAndIngredient(cocktail_name, ingredient_name), findCocktailsByTraitNameContaining(trait));
    }

    public Cocktail getCocktailByName(String name){
        return cocktailRepository.findByName(name).orElse(null);
    }

    public Recipe getRecipeByCocktail(Cocktail cocktail){
        return recipeRepository.findByCocktail(cocktail).orElse(null);
    }

    public Tag getTagById(int id){
        return tagRepository.findById(id).orElse(null);
    }

    public List<Recipe_Ingredient> getRecipeIngredientByCocktail(Cocktail cocktail){
        return recipe_ingredientRepository.findByCocktail(cocktail);
    }

    public List<Tag> findTagsByCocktail(Cocktail cocktail) {
        List<Cocktail_Tag> tags_id = cocktail_tagRepository.findByCocktail(cocktail);
        List<Tag> tags = new ArrayList<>();
        for (Cocktail_Tag cocktail_tag : tags_id) {
            Optional<Tag> tag = tagRepository.findById(cocktail_tag.getId().getTag());
            tag.ifPresent(tags::add);
        }
        return tags;
    }

    public List<Cocktail> findIntersection(List<Cocktail> list1, List<Cocktail> list2) {
        Set<Cocktail> set1 = new HashSet<>(list1);
        return list2.stream()
                .filter(set1::contains)  // list1에 존재하는 요소만 필터링
                .collect(Collectors.toList());  // 결과를 List로 변환
    }
}
