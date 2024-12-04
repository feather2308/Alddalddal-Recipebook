package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.service.CocktailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    CocktailService cocktailService;

    @GetMapping("/")
    public String mainGet(HttpSession session, Model model) {
        List<Cocktail> cocktailList = cocktailService.getAllCocktails();
        model.addAttribute("cocktails", cocktailList);

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }
        return "main";
    }
}
