package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage(Model model, String error, String logout) {
        model.addAttribute("today", new Date().toString());

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping("/login/customer")
    public String showOnlineBankingSite(Model model, String error, String logout) {
        //todo login user
        model.addAttribute("today", new Date().toString());
        return "customer_index";
    }

    @RequestMapping("/customer/new")
    public String showRegisterPage(Model model) {
        Customer c = new Customer();
        model.addAttribute("customer", c);
        return "register";
    }

    @RequestMapping("/customer")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        //todo registrieren
        return "register";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("today", new Date().toString());
        return "login";
    }
}
