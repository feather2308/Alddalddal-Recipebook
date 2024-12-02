package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/signin")
    public String signinGet() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signinPost(@RequestParam String id,   @RequestParam String password,
                             @RequestParam String name, @RequestParam String nickname,
                             @RequestParam String email) {
        accountService.add(id, password, name, nickname, email);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpSession session, RedirectAttributes redirectAttributes) {
        // 로그인 처리 로직을 추가합니다. 예시로 아이디와 비밀번호 검증.
        if (accountService.authenticate(id, password)) {
            // 로그인 성공 시 메인 페이지로 리디렉션
            session.setAttribute("user", id);
            return "redirect:/";
        } else {
            // 로그인 실패 시 오류 메시지와 함께 로그인 페이지로 다시 이동
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login"; // login.html로 다시 포워딩
        }
    }


    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypageGet(HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");
        Account account = accountService.getAccount(id);

        model.addAttribute("account", account);
        return "mypage";
    }
}
