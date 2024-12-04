package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.*;
import com.team9.alddalddal.service.AccountService;
import com.team9.alddalddal.service.CocktailService;
import com.team9.alddalddal.service.CommentsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Controller
public class CocktailController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/cocktail")
    public String cocktail(@RequestParam(defaultValue = "1") int page, HttpSession session, Model model) {
        if (page != 1){
            session.setAttribute("history", "cocktail");
        } else {
            session.setAttribute("history", "cocktail?page=" + page);
        }

        String user = (String) session.getAttribute("user");
        model.addAttribute("user", user);

        Page<Cocktail> cocktails = cocktailService.getPageCocktails(page);

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
    public String recipeGet(@RequestParam String cocktail, HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");

        Cocktail cocktail1 = cocktailService.getCocktailByName(cocktail);
        Recipe recipe = cocktailService.getRecipeByName(cocktail);
        List<Recipe_Ingredient> ingredient = cocktailService.getRecipeIngredientByCocktailName(cocktail);
        List<Tag> tags = cocktailService.findTagsByName(cocktail);
        List<Comments> comments = commentsService.getListCommentsByName(cocktail);

        if(id != null){
            model.addAttribute("id", id);
            Optional<Favorite> favoriteFlag = accountService.getFavorite(id, cocktail);
            if (favoriteFlag.isPresent()) {
                model.addAttribute("favoriteFlag", true);
            } else {
                model.addAttribute("favoriteFlag", false);
            }
        }

        String history = (String) session.getAttribute("history");
        model.addAttribute("history", history == null ? "" : history);

        model.addAttribute("cocktail", cocktail1);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredient);
        model.addAttribute("tags", tags);
        model.addAttribute("comments", comments);

        return "recipe";
    }

    @PostMapping("/recipe")
    public String recipePost(@RequestParam String cocktail,
                             @RequestParam String content,
                             HttpSession session, Model model){
        String id = (String) session.getAttribute("user");

        Date time = new Date(System.currentTimeMillis());
        System.out.println();
        System.out.println(time);
        System.out.println(content);
        System.out.println(cocktail);
        System.out.println(id);
        System.out.println();
        commentsService.createComments(time, content, cocktail, id);

        return "redirect:/recipe?cocktail=" + cocktail;
    }

    @GetMapping("/ingredient")
    public String ingredientGet(Model model) {
        List<Ingredient> ingredients = cocktailService.getListIngredients();
        model.addAttribute("ingredient", ingredients);
        return "ingredient";
    }
}
