package com.team9.alddalddal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        model.addAttribute("isLoggedIn", false);
        if (id != null) {
            model.addAttribute("isLoggedIn", true);
        }
        return "main";
    }
}
