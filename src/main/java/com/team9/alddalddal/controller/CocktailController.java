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
        Account account = accountService.getAccount(user);
        model.addAttribute("user", user);

        Page<Cocktail> cocktails = cocktailService.getPageCocktails(page);

        if (user != null) {
            List<Boolean> favoriteFlags = new ArrayList<>();
            for (Cocktail cocktail : cocktails.getContent()) {
                boolean isFavorite = accountService.isFavorite(account, cocktail);
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
    public String recipeGet(@RequestParam(name = "cocktail") String cocktail_name, HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");

        Cocktail cocktail = cocktailService.getCocktailByName(cocktail_name);
        Recipe recipe = cocktailService.getRecipeByCocktail(cocktail);

        if(id != null){
            model.addAttribute("id", id);
            Account account = accountService.getAccount(id);
            model.addAttribute("favoriteFlag", accountService.isFavorite(account, cocktail));
        }

        String history = (String) session.getAttribute("history");
        model.addAttribute("history", history == null ? "" : history);

        model.addAttribute("cocktail", cocktail);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", cocktail.getRecipeIngredientList());
        model.addAttribute("tags", cocktail.getCocktailTagList());
        model.addAttribute("comments", cocktail.getCommentList());

        return "recipe";
    }

    @PostMapping("/recipe")
    public String recipePost(@RequestParam(name = "cocktail") String cocktail_name,
                             @RequestParam String content,
                             HttpSession session, Model model){

        String id = (String) session.getAttribute("user");

        Account account = accountService.getAccount(id);
        Cocktail cocktail = cocktailService.getCocktailByName(cocktail_name);

        Date time = new Date(System.currentTimeMillis());

        commentsService.createComments(time, content, account, cocktail);

        return "redirect:/recipe?cocktail=" + cocktail.getName();
    }

    @GetMapping("/ingredient")
    public String ingredientGet(Model model) {
        List<Ingredient> ingredients = cocktailService.getAllIngredients();
        model.addAttribute("ingredient", ingredients);
        return "ingredient";
    }
}
