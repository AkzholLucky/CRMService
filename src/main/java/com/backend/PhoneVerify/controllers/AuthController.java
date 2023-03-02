package com.backend.PhoneVerify.controllers;

import com.backend.PhoneVerify.models.User;
import com.backend.PhoneVerify.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() throws IOException {
        return "auth/login";
    }

    @GetMapping("/reset")
    public String reset(@ModelAttribute("user") User user) {
        return "auth/reset";
    }

    @PostMapping("/resetPass")
    public String resetPass(@ModelAttribute("user") User user, Model model) throws IOException {
        Boolean existUser = userService.findUserByNameAndNumber(user.getName(), user.getNumber());

        if (existUser){
            model.addAttribute("message", "Check sms and submit code!");
            return "auth/newPass";
        }

        model.addAttribute("message", "Incorrect name or number!");
        return "auth/reset";
    }

    @GetMapping("/newPass")
    public String newPass(@ModelAttribute("user") User user) {
        return "auth/newPass";
    }

    @PostMapping("/newPass")
    public String changePass(@RequestParam("code") String sendCode, Model model){
        User user = userService.findByCode(sendCode);

        if (user != null){
            model.addAttribute("user", user);
            return "auth/changePass";
        }

        model.addAttribute("message", "Incorrect code");
        return "auth/newPass";
    }

    @PostMapping("/changePass/{code}")
    public String saveChangedPass(@PathVariable String code, @ModelAttribute("user") User user, Model model){
        model.addAttribute("message", "Password was changed");
        userService.findByCodeAndSave(code, user);
        return "auth/login";
    }
}