package de.othr.sw.bank.controller;


import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.service.CustomerServiceIF;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("mgmt/customers")
public class CustomerManagementController {

    @Autowired
    CustomerServiceIF customerService;

    @PostMapping("/delete/{cid}")
    public String deleteCustomer(Model model, @PathVariable long cid) {

        if (customerService.getActiveAccountsForUser(cid).size() > 0) {
            WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Unable to delete customer", "Error trying to delete customer with the id '" + cid + "'. \n Close all active accounts first.");
            model.addAttribute("message", message);
            return "messages";
        }

        ResponseEntity<Customer> responseEntity = customerService.deleteCustomerById(cid);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            Customer customer = responseEntity.getBody();

            WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Success, "Successfully deleted customer", String.format("Customer '%s %s' was deleted.", customer.getSurname(), customer.getForename()));
            model.addAttribute("message", message);
            return "messages";
        }

        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Unable to delete customer", "Error trying to delete customer with the id '" + cid + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @GetMapping("/{id}/address")
    public String getAddressView(Model model, @PathVariable long id) {
        ResponseEntity<Customer> responseEntity = customerService.getCustomerById(id);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {

            model.addAttribute("address", responseEntity.getBody().getAddress());
            model.addAttribute("customerId", id);
           return "employee/address";
        }

        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Unable to get address for customer", "Error trying to get address for the customer with the id '" + id + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @PostMapping("/{id}/address")
    public String updateAddress(Model model, @PathVariable long id, @ModelAttribute Address address,
                                @RequestParam(value = "action") String action) {

        if (action.equals("cancel"))
            return "redirect:/customers/" + id + "/";

        ResponseEntity responseEntity = customerService.updateAddressForUser(id, address);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String redirectString = "redirect:/customers/" + id + "/";
            return redirectString;
        }

        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Unable to update address for customer", "Error trying to update address for the customer with the id '" + id + "'.");
        model.addAttribute("message", message);
        return "messages";
    }

    @GetMapping("/search")
    public String queryCustomers(Model model, @RequestParam(name = "queryString") String queryString,
                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "number", defaultValue = "5") int number) {
        Pageable pageable = PageRequest.of(page-1,number, Sort.by("surname").ascending());
        ResponseEntity<Page<Customer>> responseEntity = customerService.queryCustomers(queryString,pageable);

        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
            Page<Customer> customersPage = responseEntity.getBody();
            model.addAttribute("customers", customersPage.getContent());
            model.addAttribute("customer",false);

            model.addAttribute("queryCustomers",true);
            model.addAttribute("queryString",queryString);
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",customersPage.getTotalPages());


            Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("name", employee.getForename());

            return "dashboard";
        }

        WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Warning, "Unable to find customers", "Error trying to find customers with the query string '" + queryString + "'.");
        model.addAttribute("message", message);
        return "messages";

    }
}
