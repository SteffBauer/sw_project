package de.othr.sw.bank.controller;


import de.othr.sw.bank.associateImitation.Message;
import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.service.EmployeeServiceIF;
import de.othr.sw.bank.service.SupportServiceException;
import de.othr.sw.bank.associateImitation.SupportServiceIF;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private SupportServiceIF supportService;
    @Autowired
    private EmployeeServiceIF employeeService;

    //@Value("${authentication-token-chat-service}")
    //private String authenticationToken;

    @GetMapping
    public String getSupportChatView(Model model) {
        List<Message> messages;
        try {
            messages = supportService.pullMessages("ServiceToken");
        }
        catch (SupportServiceException ex){
            WebsiteMessage message = new WebsiteMessage(WebsiteMessageType.Danger, "Support Service Error", ex.getMessage());
            model.addAttribute("message", message);
            return "messages";
        }

        model.addAttribute("messages", messages);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Employee) {
            model.addAttribute("isEmployee", true);

            Optional<Employee> employee = employeeService.getEmployeeByUsername(((Employee) authentication.getPrincipal()).getUsername());

            if(employee != null)
                model.addAttribute("customers", employee.get().getActiveCustomers());

           return "employee/supportChat.html";
        }


        model.addAttribute("isEmployee", false);
        return "customer/supportChat.html";
    }

    @PostMapping("{id}/messages/new")
    public String sendMessage(Model model, @PathVariable("id") long chatId, @RequestBody Message message){
        // todo call associate API
        // todo retrieve messages of chat and return supportChat.html (depending on emp/cust login)
        throw new NotYetImplementedException();
    }
}
