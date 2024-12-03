package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "cocktail_name", required = false) String cocktail_name,
            @RequestParam(value = "ingredient_name", required = false) String ingredient_name,
            @RequestParam(value = "trait", required = false) String trait,
            @RequestParam(value = "how2search", defaultValue = "0") int how2search,
            Model model) {

        List<Cocktail> results;

        if (how2search == 1 && cocktail_name != null && !cocktail_name.isEmpty()) {
            // 칵테일 이름으로 검색
            results = cocktailService.findCocktailsByNameContaining(cocktail_name);

        } else if (how2search == 2 && ingredient_name != null && !ingredient_name.isEmpty()) {
            // 재료로 검색
            results = cocktailService.findCocktailsByIngredient(ingredient_name);

        } else if (how2search == 3 && trait != null && !trait.isEmpty()) {
            // 특성으로 검색
            results = cocktailService.findCocktailsByTrait(trait);

        } else if (how2search == 4) {
            // 혼합 검색
            results = cocktailService.findCocktailsByNameContainingAndIngredient(cocktail_name, ingredient_name);

        } else if (how2search == 5) {
            // 혼합 검색
            results = cocktailService.findCocktailsByNameContainingAndTrait(cocktail_name, trait);

        } else if (how2search == 6) {
            // 혼합 검색
            results = cocktailService.findCocktailsByIngredientAndTrait(ingredient_name, trait);

        } else if (how2search == 7) {
            // 혼합 검색
            results = cocktailService.findCocktailsByMixedCriteria(cocktail_name, ingredient_name, trait);

        } else {
            // 검색 조건이 없는 경우 빈 리스트 반환
            results = new ArrayList<>();
        }

        model.addAttribute("results", results);
        return "search";
    }
}
