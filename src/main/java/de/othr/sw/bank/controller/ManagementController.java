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
@RequestMapping("mgmt")
public class ManagementController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;


    @RequestMapping( "/customers/delete/{cid}")
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
