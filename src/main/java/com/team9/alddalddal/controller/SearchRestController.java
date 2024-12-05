package com.team9.alddalddal.controller;

import com.team9.alddalddal.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/find")
public class SearchRestController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/getIngredients")
    public List<String> getIngredients(@RequestParam("query") String name) {
        String[] parts = name.split(",");
        String lastPart = parts[parts.length - 1].trim();
        return cocktailService.findIngredientsNameByNameContaining(lastPart);
    }

    @GetMapping("/getTraits")
    public List<String> getTraits(@RequestParam("query") String name) {
        String[] parts = name.split(",");
        String lastPart = parts[parts.length - 1].trim();
        return cocktailService.findTraitsNameByNameContaining(lastPart);
    }
}
