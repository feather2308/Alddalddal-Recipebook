package com.team9.alddalddal.controller;

import com.team9.alddalddal.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getIngredients")
public class SearchRestController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/find")
    public List<String> getIngredients(@RequestParam("query") String name) {
        return cocktailService.findIngredientsNameByNameContaining(name);
    }
}
