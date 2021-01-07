package de.othr.sw.bank.controller;


import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.WebsiteMessage;
import de.othr.sw.bank.entity.WebsiteMessageType;
import de.othr.sw.bank.service.CustomerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("mgmt")
public class CustomerManagementController {

    @Autowired
    CustomerServiceIF customerService;

    @PostMapping( "/customers/delete/{cid}")
    public String deleteCustomer(Model model, @PathVariable long cid) {

        if(customerService.getActiveAccountsForUser(cid).size()>0){
            WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to delete customer","Error trying to delete customer with the id '" + cid + "'. \n Close all active accounts first.");
            model.addAttribute("message", message);
            return "messages";
        }

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
    public String updateAddress(Model model, @PathVariable long id, @ModelAttribute Address address,
                                @RequestParam(value="action") String action) {

        if (action.equals("cancel"))
            return "redirect:/customers/" + id + "/";

        ResponseEntity responseEntity = customerService.updateAddressForUser(id,address);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            String redirectString = "redirect:/customers/" + id + "/";
            return redirectString;
        }

        WebsiteMessage message= new WebsiteMessage(WebsiteMessageType.Danger,"Unable to update address for customer","Error trying to update address for the customer with the id '" + id + "'.");
        model.addAttribute("message", message);
        return "messages";
    }
}
