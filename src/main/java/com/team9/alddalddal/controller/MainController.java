package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.service.CocktailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    CocktailService cocktailService;

    @GetMapping("/")
    public String mainGet(
            @RequestParam(value = "cocktail", required = false, defaultValue = "") String cocktail_name,
            @RequestParam(value = "ingredient", required = false, defaultValue = "") String ingredient_name,
            @RequestParam(value = "trait", required = false, defaultValue = "") String trait_name,
            HttpSession session, Model model) {
        session.setAttribute("history", "?cocktail=" + cocktail_name + "&ingredient=" + ingredient_name + "&trait=" + trait_name);

        int flag = 0;
        List<String> ingredient_names = null,
                trait_names = null;

        // `flag` 값 설정
        if (cocktail_name != null && !cocktail_name.isEmpty()) {
            flag += 4;
        }
        if (ingredient_name != null && !ingredient_name.isEmpty()) {
            flag += 2;
            ingredient_names = toList(ingredient_name);
        }
        if (trait_name != null && !trait_name.isEmpty()) {
            flag += 1;
            trait_names = toList(trait_name);
        }

        List<Cocktail> results = switch (flag) {
            case 0 ->
                // 모든 값이 null 또는 빈 리스트인 경우
                    new ArrayList<>();

            case 1 ->
                // trait만 값이 있는 경우
                    cocktailService.findCocktailsByTraitNameContaining(
                            (trait_names != null && trait_names.size() > 1) ? trait_names : trait_name
                    );

            case 2 ->
                // ingredient_name만 값이 있는 경우
                    cocktailService.findCocktailsByIngredientNameContaining(
                            (ingredient_names != null && ingredient_names.size() > 1) ? ingredient_names : ingredient_name
                    );

            case 3 ->
                // ingredient_name과 trait에 값이 있는 경우
                    cocktailService.findCocktailsByIngredientAndTrait(
                            (ingredient_names != null && ingredient_names.size() > 1) ? ingredient_names : ingredient_name,
                            (trait_names != null && trait_names.size() > 1) ? trait_names : trait_name
                    );

            case 4 ->
                // cocktail_name만 값이 있는 경우
                    cocktailService.findCocktailsByNameContaining(cocktail_name);

            case 5 ->
                // cocktail_name과 trait에 값이 있는 경우
                    cocktailService.findCocktailsByNameContainingAndTrait(
                            cocktail_name,
                            (trait_names != null && trait_names.size() > 1) ? trait_names : trait_name
                    );

            case 6 ->
                // cocktail_name과 ingredient_name에 값이 있는 경우
                    cocktailService.findCocktailsByNameContainingAndIngredient(
                            cocktail_name,
                            (ingredient_names != null && ingredient_names.size() > 1) ? ingredient_names : ingredient_name
                    );

            case 7 ->
                // 모든 값에 값이 있는 경우
                    cocktailService.findCocktailsByMixedCriteria(
                            cocktail_name,
                            (ingredient_names != null && ingredient_names.size() > 1) ? ingredient_names : ingredient_name,
                            (trait_names != null && trait_names.size() > 1) ? trait_names : trait_name
                    );

            default ->
                // 기본 값
                    new ArrayList<>();
        };

        String history = (String) session.getAttribute("history");
        model.addAttribute("history", history == null ? "" : history);

        model.addAttribute("results", results);

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

        return "main";
    }

    public static List<String> toList(String input) {
        // 1. 문자열을 쉼표로 나눔
        // 2. 공백 제거
        // 3. 중복 제거
        return Arrays.stream(input.split(","))
                .map(String::trim)  // 앞뒤 공백 제거
                .filter(s -> !s.isEmpty()) // 빈 문자열 필터링
                .distinct() // 중복 제거
                .collect(Collectors.toList());
    }
}
