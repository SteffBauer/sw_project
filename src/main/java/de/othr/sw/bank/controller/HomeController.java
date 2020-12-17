package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.CustomerRepositoryIF;
import de.othr.sw.bank.service.BankingService;
import de.othr.sw.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "index";
    }

    @RequestMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("today", new Date().toString());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Customer customer = (Customer) authentication.getPrincipal();


            List<Account> accounts = customerService.getAccountsForUser(customer.getId());
            model.addAttribute("accounts", accounts);


            return "dashboard";

        }
        return "login";
    }
}
