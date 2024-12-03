package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.repository.Recipe_IngredientRepository;
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
            Model model) {

        List<Cocktail> results;

        int flag = 0;

        // `flag` 값 설정
        if (cocktail_name != null && !cocktail_name.isEmpty()) {
            flag += 4;
        }
        if (ingredient_name != null && !ingredient_name.isEmpty()) {
            flag += 2;
        }
        if (trait != null && !trait.isEmpty()) {
            flag += 1;
        }

        System.out.println(flag);

        // `switch` 문 작성
        results = switch (flag) {
            case 0 ->
                // 모든 값이 null인 경우
                    new ArrayList<>();
            case 1 ->
                // trait만 값이 있는 경우
                // 특성으로 검색
                    cocktailService.findCocktailsByTrait(trait);
            case 2 ->
                // ingredient_name만 값이 있는 경우
                // 재료로 검색
                    cocktailService.findCocktailsByIngredient(ingredient_name);
            case 3 ->
                // ingredient_name과 trait에 값이 있는 경우
                // 재료, 특성으로 검색
                    cocktailService.findCocktailsByIngredientAndTrait(ingredient_name, trait);
            case 4 ->
                // cocktail_name만 값이 있는 경우
                // 칵테일 이름으로 검색
                    cocktailService.findCocktailsByNameContaining(cocktail_name);
            case 5 ->
                // cocktail_name과 trait에 값이 있는 경우
                // 칵테일 이름, 특성으로 검색
                    cocktailService.findCocktailsByNameContainingAndTrait(cocktail_name, trait);
            case 6 ->
                // cocktail_name과 ingredient_name에 값이 있는 경우
                // 칵테일 이름, 재료로 검색
                    cocktailService.findCocktailsByNameContainingAndIngredient(cocktail_name, ingredient_name);
            case 7 ->
                // 모든 값에 값이 있는 경우
                // 혼합 검색
                    cocktailService.findCocktailsByMixedCriteria(cocktail_name, ingredient_name, trait);
            default -> new ArrayList<>();
        };

        model.addAttribute("results", results);
        return "search";
    }
}
