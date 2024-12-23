package com.team9.alddalddal.controller;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.service.AccountService;
import com.team9.alddalddal.service.CocktailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/signin")
    public String signinGet(HttpSession session, Model model) {
        if(session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        return "signin";
    }

    @PostMapping("/signin")
    public String signinPost(@RequestParam String id,   @RequestParam String password,
                             @RequestParam String name, @RequestParam String nickname,
                             @RequestParam String email,
                             HttpSession session) {
        String id_ = id.trim();

        if(accountService.getAccount(id_) != null) {
            session.setAttribute("error", "이미 존재하는 아이디입니다.");
            return "redirect:/signin";
        }
        accountService.add(id_, password, name, nickname, email);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpSession session, Model model) {
        // 로그인 처리 로직을 추가합니다. 예시로 아이디와 비밀번호 검증.
        if (accountService.authenticate(id, password)) {
            // 로그인 성공 시 메인 페이지로 리디렉션
            session.setAttribute("user", id);
            return "redirect:/";
        } else {
            // 로그인 실패 시 오류 메시지와 함께 로그인 페이지로 다시 이동
            model.addAttribute("error", true);
            return "login"; // login.html로 다시 포워딩
        }
    }

    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/findid")
    public String findIdGet(Model model) {
        return "findid";
    }

    @PostMapping("/findid")
    public String findIdPost(@RequestParam("email") String email,
                             Model model) {
        Account account = accountService.getAccountByEmail(email);
        if (account == null) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("account", account);
        }

        return "findid";
    }

    @GetMapping("/findpw")
    public String findPwGet(Model model) {
        return "findpw";
    }

    @PostMapping("/findpw")
    public String findPwPost(@RequestParam("id") String id,
                             @RequestParam("email") String email,
                             Model model) {
        Account account = accountService.getAccount(id);
        if (account == null || !account.getEmail().equals(email)) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("account", account);
        }

        return "findpw";
    }

    @GetMapping("/mypage")
    public String mypageGet(HttpSession session, Model model) {
        session.setAttribute("history", "mypage");

        String id = (String) session.getAttribute("user");
        Account account = accountService.getAccount(id);

        List<Favorite> favorites = accountService.getFavoritesByAccount(account);

        List<Cocktail> cocktails = new ArrayList<>();
        for (Favorite favorite : favorites) {
            String name = favorite.getId().getName();
            Cocktail cocktail = cocktailService.getCocktailByName(name);
            if(cocktail != null) {
                cocktails.add(cocktail);
            }
        }

        model.addAttribute("cocktails", cocktails);
        model.addAttribute("account", account);

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

        return "mypage";
    }

    @GetMapping("/edit-profile")
    public String editProfileGet(HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");
        Account account = accountService.getAccount(id);

        model.addAttribute("account", account);

        String user = (String) session.getAttribute("user");
        model.addAttribute("isLoggedIn", false);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
        }

        return "editprofile";
    }

    @PostMapping("/edit-profile")
    public String editProfilePost(
            HttpSession session,
            @RequestParam("name") String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            Model model) {

        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("user");
        Account account = accountService.getAccount(id);

        // 현재 비밀번호 검증
        if (!account.getPw().equals(currentPassword)) {
            model.addAttribute("error", "현재 비밀번호가 올바르지 않습니다.");
            model.addAttribute("account", account); // 기존 데이터 다시 전달
            return "editprofile";
        }

        // 사용자 정보 업데이트
        account.setName(name);
        account.setNickname(nickname);
        account.setEmail(email);

        if (newPassword != null && !newPassword.isEmpty()) {
            account.setPw(newPassword); // 새 비밀번호가 있을 경우 업데이트
        }

        // 데이터베이스 업데이트
        accountService.updateAccount(account);

        // 성공 메시지 설정
        model.addAttribute("success", "프로필이 성공적으로 수정되었습니다.");
        return "redirect:/mypage"; // 수정 후 마이페이지로 리다이렉트
    }

    @GetMapping("/resign")
    public String resignGet(HttpSession session, Model model) {
        String id = (String) session.getAttribute("user");
        session.invalidate();

        Account account = accountService.getAccount(id);
        accountService.deleteAccount(account);

        return "redirect:/";
    }
}
