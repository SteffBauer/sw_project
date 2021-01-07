package de.othr.sw.bank.controller;


import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/support")
public class SupportController {

    @GetMapping
    public String getSupportChat(Model model){
        throw new NotYetImplementedException();
    }
}
