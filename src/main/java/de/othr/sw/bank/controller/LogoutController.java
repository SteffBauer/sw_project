package de.othr.sw.bank.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LogoutController {
    @RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("today", new Date().toString());
        return "login";
    }
}
