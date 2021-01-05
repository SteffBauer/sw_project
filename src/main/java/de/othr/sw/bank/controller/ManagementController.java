package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.utils.WebsiteMessageUtils;
import org.hibernate.cfg.NotYetImplementedException;
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


@Controller
@RequestMapping("mgmt")
public class ManagementController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;


    @PostMapping( "/customers/delete/{cid}")
    public String deleteCustomer(Model model, @PathVariable long cid) {

        // todo check no open accounts -> Requirements
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

    @GetMapping( "/customers/{id}/address")
    public String getAddressView(Model model, @PathVariable long id) {

        ResponseEntity<Customer> responseEntity = customerService.getCustomerById(id);
        if(responseEntity.getStatusCode() ==  HttpStatus.OK && responseEntity.getBody() != null) {
            model.addAttribute("address", responseEntity.getBody().getAddress());
            model.addAttribute("customerId", id);
            return "/employee/address";
        }

        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to get address for customer","Error trying to get address for the customer with the id '" + id + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @PostMapping( "/customers/{id}/address")
    public String updateAddress(Model model, @PathVariable long id, @ModelAttribute Address address) {

        ResponseEntity responseEntity = customerService.updateAddressForUser(id,address);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            String redirectString = "redirect:/customers/" + id + "/";
            return redirectString;
        }
        // error case
        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to update address for customer","Error trying to update address for the customer with the id '" + id + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @PostMapping("/accounts/{id}/delete")
    public String deleteAccount(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();


        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to delete the account.");


        if(account.getBalance() != 0){
            return "redirect:/mgmt/accounts/"+account.getId()+"/delete/confirmation";
        }


        return deleteAccount(model, account);
    }

    @GetMapping("/accounts/{id}/delete/confirmation")
    public String deleteAccountConfirmation(Model model, @PathVariable long id) {
        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);
        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();
        model.addAttribute("account", account);
        return "/employee/deleteAccountConfirmation";
    }

    @PostMapping("/accounts/{id}/delete/confirmed")
    public String deleteAccountConfirmed(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();

        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to delete the account.");

        return deleteAccount(model, account);

    }



    private String deleteAccount(Model model, Account account) {
        ResponseEntity<Account> responseEntity = bankingService.deleteAccount(account.getId());
        if(responseEntity.getStatusCode()!= HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Failed to delete account", "Something went wrong, while trying to delete the account with the id '"+account.getId()+"'.");

        return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Success, "Account deleted", "Successfully deleted the account with the iban '" + account.getIban() + "'.");
    }
}
