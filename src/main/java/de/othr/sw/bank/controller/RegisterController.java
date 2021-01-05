package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.WebsiteMessage;
import de.othr.sw.bank.entity.WebsiteMessageType;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.service.TaxNumberAlreadyRegisteredException;
import de.othr.sw.bank.service.UsernameAlreadyInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    CustomerServiceIF customerServiceIF;

    @RequestMapping
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping
    public String registerCustomer(Model model, @ModelAttribute Customer customer) {
        ResponseEntity responseEntity;
        try {
            responseEntity = customerServiceIF.createCustomer(customer);

        } catch (UsernameAlreadyInUseException e) {
            model.addAttribute("customer", customer);
            model.addAttribute("invalidUsername", customer.getUsername());
            return "register";
        } catch (TaxNumberAlreadyRegisteredException e) {
            model.addAttribute("customer", customer);
            model.addAttribute("invalidTaxNr", customer.getTaxNumber());
            return "register";
        }

        WebsiteMessage message;

        if (responseEntity.getStatusCode() == HttpStatus.CREATED)
            message = new WebsiteMessage(WebsiteMessageType.Success, "Successfully registered", "You are registered and can log in now.");
        else
            message = new WebsiteMessage(WebsiteMessageType.Danger, "Registration Error", "There occurred an error while trying to registrate the user.");

        model.addAttribute("message", message);
        return "messages";
    }
}
