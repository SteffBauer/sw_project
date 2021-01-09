package de.othr.sw.bank.controller;


import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.WebsiteMessageType;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.utils.WebsiteMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mgmt/accounts")
public class BankingManagementController {

    @Autowired
    BankingServiceIF bankingService;

    @PostMapping("/{id}/delete")
    public String deleteAccount(Model model, @PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");
        Account account = optionalAccount.getBody();

        // Authenticated User has to be a Employee or the owner of the account
        if (authentication.getPrincipal() instanceof Customer && ((Customer) authentication.getPrincipal()).getId() != account.getCustomer().getId())
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Access denied", "You are not allowed to delete the account.");

        if(account.getBalance() != 0)
            return "redirect:/mgmt/accounts/"+account.getId()+"/delete/confirmation";

        return deleteAccount(model, account);
    }

    @GetMapping("/{id}/delete/confirmation")
    public String deleteAccountConfirmation(Model model, @PathVariable long id) {
        ResponseEntity<Account> optionalAccount = bankingService.getAccountById(id);

        if (optionalAccount.getStatusCode() != HttpStatus.OK || optionalAccount.getBody() == null)
            return WebsiteMessageUtils.showWebsiteMessage(model, WebsiteMessageType.Danger, "Wrong account", "Not able to delete the account with the id '" + id + "'.");

        Account account = optionalAccount.getBody();
        model.addAttribute("account", account);

        return "/employee/deleteAccountConfirmation";
    }

    @PostMapping("/{id}/delete/confirmed")
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
