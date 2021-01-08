package de.othr.sw.bank.controller;


import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.entity.Message;
import de.othr.sw.bank.entity.Person;
import de.othr.sw.bank.service.SupportServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private SupportServiceIF supportService;

    @GetMapping
    public String getSupportChat(Model model) {
        // todo for testing website
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //long id = ((Person) authentication.getPrincipal()).getId();
        List<Message> messages = supportService.pullMessages("ServiceToken");
        model.addAttribute("messages", messages);
/*
        if (authentication.getPrincipal() instanceof Employee) {
            model.addAttribute("isEmployee", true);
            return "/employee/supportChat.html";
        }

 */
        model.addAttribute("isEmployee", false);
        return "/customer/supportChat.html";


    }
}
