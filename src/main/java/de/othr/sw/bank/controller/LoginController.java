package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginPage(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "registered", required = false) String registered) {

        model.addAttribute("customer", new Customer());

        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");
        else if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        else if (registered != null)
            model.addAttribute("message", "Your registration was successful.");

        return "login";
    }


}
