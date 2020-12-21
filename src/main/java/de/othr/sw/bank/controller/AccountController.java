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
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    CustomerServiceIF customerService;
    @Autowired
    BankingServiceIF bankingService;

    @RequestMapping("/opening")
    public String getFormForNewAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            model.addAttribute("username", currentUserName);
        }

        AccountRequest accountRequest = new AccountRequest();
        model.addAttribute("accountRequest", accountRequest);

        return "/customer/account_apply";
    }

    @PostMapping
    public String applyForNewAccount(Model model, @ModelAttribute AccountRequest accountRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Customer customer = (Customer) authentication.getPrincipal();
            accountRequest.setUsername(customer.getUsername());

            ResponseEntity<AccountRequest> responseEntity = customerService.createAccount(accountRequest);

            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Success, "Successfully applied", "Your request for an account has been sent. It will be verified by our employees.");
                model.addAttribute("message", message);
                return "messages";
            } else {
                WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Error while processing your apply", "An error occurred while sending your request.");
                model.addAttribute("message", message);
                return "messages";
            }

        }
        return "login";
    }

    @GetMapping("/{id}")
    public String getAccountView(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity responseEntity = bankingService.getAccountById(id);

        if (responseEntity.getStatusCode() != HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Account info not reachable", "Error while accessing your account info.");

        Account account = (Account) responseEntity.getBody();

        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to get information about the account.");

        ResponseEntity<List<Transfer>> responseEntityTransfers = bankingService.getTransfersByAccountId(account.getId());

        if (responseEntityTransfers.getStatusCode() == HttpStatus.OK)
            model.addAttribute("transfers", responseEntityTransfers.getBody());
        else
            model.addAttribute("transfers", null);
        model.addAttribute("account", account);
        return "/customer/account";


    }


    @GetMapping("/{id}/transfers/new")
    public String getTransferView(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "No transfers can be made to the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();

        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to make transfers for the account.");

        String currentUserName = authentication.getName();

        model.addAttribute("username", currentUserName);

        Transfer transfer = new Transfer();
        model.addAttribute("transfer", transfer);

        //todo z.B. überweisung nur bis 500 € im Minus möglich?
        return "/customer/account_transfer";

    }

    @DeleteMapping("/{id}/delete")
    public String deleteAccount(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();


        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to delete the account.");


        //todo check balance, delete..
        return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Success, "Account deleted", "Successfully deleted the account with the iban '" + account.getIban() + "'.");

    }

}
