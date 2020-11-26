package de.othr.sw.bank.controller;

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
    public String showLoginPage(Model model, @ModelAttribute("username") String username, @ModelAttribute("password") String password) {
        //todo login user
        model.addAttribute("today", new Date().toString());
        return "login";
    }

    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "register";
    }

/*
    @RequestMapping("/customer/{id}")
    public String prepareStudentPage(Model model, @PathVariable("id") long cId) {

        ResponseEntity<Customer> customer = customerService.findCustomer(cId);
        model.addAttribute("studentslist", customer.getBody());

        return "showCustomer";
    }
*/
}
