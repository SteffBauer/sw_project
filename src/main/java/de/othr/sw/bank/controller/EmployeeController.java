package de.othr.sw.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller @RequestMapping("/employee")
public class EmployeeController {
    @RequestMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "employeeIndex";
    }
}
