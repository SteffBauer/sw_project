package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String showLoginPage(Model model, String error, String logout) {
        model.addAttribute("today", new Date().toString());

        Customer c = new Customer();
        model.addAttribute("customer", c);

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @PutMapping("/login")
    public String loginPerson(Model model, Customer customer){
        System.out.println("success");

        // Login data is incorrect
        return showLoginPage(model,"error", null);

    }
}
