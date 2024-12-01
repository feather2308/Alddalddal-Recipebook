package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Ingredient;
import com.team9.alddalddal.entity.Recipe;
import com.team9.alddalddal.service.CocktailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CocktailController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/cocktail")
    public String cocktail(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
        Page<Cocktail> cocktails = cocktailService.listCocktails(page);

        model.addAttribute("cocktails", cocktails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cocktails.getTotalPages());

        return "cocktail";
    }

    @GetMapping("/recipe")
    public String recipe(@RequestParam String cocktail, HttpSession session, Model model) {
        Cocktail cocktail1 = cocktailService.getCocktailByName(cocktail);
        Recipe recipe = cocktailService.getRecipeByName(cocktail);

        model.addAttribute("cocktail", cocktail1);
        model.addAttribute("recipe", recipe);

        return "recipe";
    }

    @GetMapping("/ingredient")
    public String ingredient(Model model) {
        List<Ingredient> ingredients = cocktailService.listIngredients();
        model.addAttribute("ingredient", ingredients);
        return "ingredient";
    }
}
