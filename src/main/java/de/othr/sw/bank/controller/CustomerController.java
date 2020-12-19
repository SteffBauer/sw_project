package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Transfer;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.CustomerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;
    @Autowired
    HomeController homeController;

    @RequestMapping("customers/{cid}")
    public String getCustomerView(Model model, @PathVariable long cid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            model.addAttribute("username", currentUserName);
        }

        Optional<Customer> optionalCustomer= customerService.getCustomerById(cid);

        if(!optionalCustomer.isEmpty()) {
            Customer customer = optionalCustomer.get();

            List<Account> accounts = customerService.getAccountsForUser(customer.getId());
            model.addAttribute("accounts", accounts);

            long sum = 0L;
            for (Account account : accounts) {
                sum += account.getBalance();
            }

            model.addAttribute("totalBalance", sum);

            model.addAttribute("customer", customer);
            return "/employee/customer";
        }

        return homeController.showDashboard(model,"Error trying to get view for customer.",null);
    }

}
