package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.*;
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
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            ResponseEntity responseEntity = bankingService.getAccountById(id);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                Account account = (Account) responseEntity.getBody();

                ResponseEntity<List<Transfer>> responseEntityTransfers = bankingService.getTransfersByAccountId(account.getId());

                if (responseEntityTransfers.getStatusCode() == HttpStatus.OK)
                    model.addAttribute("transfers", responseEntityTransfers.getBody());
                else
                    model.addAttribute("transfers", null);
                model.addAttribute("account", account);
                return "/customer/account";
            }

        }
        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Account info not reachable", "Error while accessing your account info.");
        model.addAttribute("message", message);
        return "messages";

    }


    @GetMapping("/{id}/transfers/new")
    public String getTransferView(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            model.addAttribute("username", currentUserName);

            Transfer transfer = new Transfer();
            model.addAttribute("transfer", transfer);

            //todo z.B. überweisung nur bis 500 € im Minus möglich?
            return "/customer/account_transfer";
        }

        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Could not make a transfer","Error while trying to make new transfer.");
        model.addAttribute("message", message);
        return "messages";

    }

    @DeleteMapping("/{id}/delete")
    public String deleteAccount(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Customer customer = (Customer) authentication.getPrincipal();
            ResponseEntity responseEntity = bankingService.getAccountById(id);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                Account account = (Account) responseEntity.getBody();


                if (account.getCustomer().getId() != customer.getId()) {
                    //return homeController.showDashboard(model, "Error while accessing account info.", null);
                }
                //todo check balance, delete..
                WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Success, "Account deleted", "Successfully deleted the account with the iban '"+account.getIban()+"'.");
                model.addAttribute("message", message);
                return "messages";
            }
        }

        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Could not delete account", "Error trying to delete your account.");
        model.addAttribute("message", message);
        return "messages";
    }


}
