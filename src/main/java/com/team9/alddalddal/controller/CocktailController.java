package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.*;
import com.team9.alddalddal.service.AccountService;
import com.team9.alddalddal.service.CocktailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CocktailController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/cocktail")
    public String cocktail(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");
        model.addAttribute("user", user);

        Page<Cocktail> cocktails = cocktailService.listCocktails(page);

        if (user != null) {
            List<Boolean> favoriteFlags = new ArrayList<>();
            for (Cocktail cocktail : cocktails.getContent()) {
                boolean isFavorite = accountService.isFavorite(user, cocktail.getName());
                favoriteFlags.add(isFavorite);
            }
            model.addAttribute("favoriteFlags", favoriteFlags);
        }

        model.addAttribute("cocktails", cocktails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cocktails.getTotalPages());

        return "cocktail";
    }

    @GetMapping("/recipe")
    public String recipe(@RequestParam String cocktail, HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");

        Cocktail cocktail1 = cocktailService.getCocktailByName(cocktail);
        Recipe recipe = cocktailService.getRecipeByName(cocktail);
        List<Recipe_Ingredient> ingredient = cocktailService.getRecipeIngredientByCocktailName(cocktail);

        if(id != null){
            model.addAttribute("id", id);
            Optional<Favorite> favoriteFlag = accountService.getFavorite(id, cocktail);
            if (favoriteFlag.isPresent()) {
                model.addAttribute("favoriteFlag", true);
            } else {
                model.addAttribute("favoriteFlag", false);
            }
        }

        model.addAttribute("cocktail", cocktail1);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredient);

        return "recipe";
    }

    @GetMapping("/ingredient")
    public String ingredient(Model model) {
        List<Ingredient> ingredients = cocktailService.listIngredients();
        model.addAttribute("ingredient", ingredients);
        return "ingredient";
    }
}
