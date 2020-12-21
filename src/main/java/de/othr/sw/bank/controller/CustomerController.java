package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.utils.WebsiteMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;

    @RequestMapping("/{cid}")
    public String getCustomerView(Model model, @PathVariable long cid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
        }


        if ((authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != cid))
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You do not have the permission to view the customer '" + cid + "'.");
        ResponseEntity<Customer> optionalCustomer = customerService.getCustomerById(cid);

        if (optionalCustomer.getStatusCode() == HttpStatus.OK && optionalCustomer.getBody() != null) {
            Customer customer = optionalCustomer.getBody();

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
        return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Unable to view customer", "Error trying to get view for customer '" + cid + "'.");

    }

}
