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
    public String cocktail(HttpSession session, Model model) {
        session.setAttribute("history", "cocktail");


        String user = (String) session.getAttribute("user");
        Account account = accountService.getAccount(user);
        model.addAttribute("user", user);

        List<Cocktail> cocktails = cocktailService.getAllCocktails();

        if (user != null) {
            List<Boolean> favoriteFlags = new ArrayList<>();
            for (Cocktail cocktail : cocktails) {
                boolean isFavorite = accountService.isFavorite(account, cocktail);
                favoriteFlags.add(isFavorite);
            }
            model.addAttribute("favoriteFlags", favoriteFlags);
        }

        model.addAttribute("cocktails", cocktails);

        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

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
        model.addAttribute("account", accountService.getAccount(id));

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

        return "recipe";
    }

    @PostMapping("/addComment")
    public String recipePost(@RequestParam(name = "cocktail") String cocktail_name,
                             @RequestParam String content,
                             HttpSession session){

        String id = (String) session.getAttribute("user");

        Account account = accountService.getAccount(id);
        if (account == null){
            return "redirect:/login";
        }
        Cocktail cocktail = cocktailService.getCocktailByName(cocktail_name);

        Date time = new Date(System.currentTimeMillis());

        commentsService.createComments(time, content, account, cocktail);

        return "redirect:/recipe?cocktail=" + cocktail.getName();
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam(name = "cocktail") String cocktail_name,
                                @RequestParam int id,
                                HttpSession session){
        Account account = accountService.getAccount((String) session.getAttribute("user"));
        Cocktail cocktail = cocktailService.getCocktailByName(cocktail_name);

        if(commentsService.findById(id).getAccount() == account)
            commentsService.deleteCommentsById(id);

        return "redirect:/recipe?cocktail=" + cocktail.getName();
    }

    @GetMapping("/ingredient")
    public String ingredientGet(HttpSession session, Model model) {
        List<Ingredient> ingredients = cocktailService.getAllIngredients();
        model.addAttribute("ingredient", ingredients);

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

        return "ingredient";
    }
}
