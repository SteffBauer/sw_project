package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.service.CustomerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RegisterController {
    @Autowired
    CustomerServiceIF customerServiceIF;
    @Autowired
    LoginController loginController;

    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        Customer c = new Customer();
        model.addAttribute("customer", c);
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(Model model,@ModelAttribute Customer customer) {
        ResponseEntity responseEntity = customerServiceIF.createCustomer(customer);
        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return loginController.showLoginPage(model,null,null,"registered");
        }
        else {
            model.addAttribute("customer", customer);
            model.addAttribute("invalidTaxNr", "Error from Spring");
            model.addAttribute("invalidUsername", "Error from Spring");
            return "register";
        }
    }
}
