package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.WebsiteMessage;
import de.othr.sw.bank.entity.WebsiteMessageType;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;

    @RequestMapping( "customers/{cid}")
    public String getCustomerView(Model model, @PathVariable long cid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            model.addAttribute("username", currentUserName);
        }

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


        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to view customer","Error trying to get view for customer '" + cid + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @RequestMapping( "customers/delete/{cid}")
    public String deleteCustomer(Model model, @PathVariable long cid) {

        // todo check account balances, and if customer is related to logged in employee
        ResponseEntity<Customer> responseEntity = customerService.deleteCustomerById(cid);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            Customer customer = responseEntity.getBody();

            WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Success,"Successfully deleted customer",String.format("Customer '%s %s' was deleted.", customer.getSurname(), customer.getForename()));
            model.addAttribute("message", message);
            return "messages";
        }

        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to delete customer","Error trying to delete customer with the id '" + cid + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

}
