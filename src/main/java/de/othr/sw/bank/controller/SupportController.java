package de.othr.sw.bank.controller;


import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.entity.Message;
import de.othr.sw.bank.entity.Person;
import de.othr.sw.bank.service.EmployeeServiceIF;
import de.othr.sw.bank.service.SupportServiceIF;
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

    @GetMapping
    public String getSupportChatView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id = ((Person) authentication.getPrincipal()).getId();
        List<Message> messages = supportService.pullMessages("ServiceToken");
        model.addAttribute("messages", messages);

        if (authentication.getPrincipal() instanceof Employee) {
            model.addAttribute("isEmployee", true);

            Optional<Employee> employee = employeeService.getEmployeeByUsername(((Employee) authentication.getPrincipal()).getUsername());

            if(employee != null)
                model.addAttribute("customers", employee.get().getActiveCustomers());

           return "employee/supportChat.html";
        }


        model.addAttribute("isEmployee", false);
        //return "/customer/supportChat.html";
       return "employee/supportChat.html";


    }

    @PostMapping("{id}/messages/new")
    public String sendMessage(Model model, @PathVariable("id") long chatId, @RequestBody Message message){
        throw new NotYetImplementedException();
    }
}
