package com.team9.alddalddal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }
        return "main";
    }
}
