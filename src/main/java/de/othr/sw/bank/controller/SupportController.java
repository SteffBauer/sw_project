package de.othr.sw.bank.controller;


import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFSendMessage;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.entity.WebsiteMessage;
import de.othr.sw.bank.entity.WebsiteMessageType;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.service.EmployeeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/support")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SupportController {

    @Autowired
    private IFSendMessage supportService;
    @Autowired
    private EmployeeServiceIF employeeService;
    @Autowired
    private CustomerServiceIF customerService;

    // For handling support service messages in the session scope
    private LocalDateTime creationTime;

    public SupportController() {
        creationTime = LocalDateTime.now();
    }

    @GetMapping
    public String getSupportChatView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return getMessagesForUser(model, authentication, currentUserName);
    }

    @PostMapping("{username}/messages/new")
    public String sendMessage(Model model, @ModelAttribute Message message, @PathVariable("username") String customerUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new Person();

        // Handling customer and employee message requests
        if (authentication.getPrincipal() instanceof Employee)
            user.setUsername("bank_service");
        else {
            String currentUserName = authentication.getName();
            user.setUsername(currentUserName);
        }
        message.setAuthor(user);


        // Get chat of the customer with the id
        Optional<Customer> optionalCustomer = customerService.getCustomerByUsername(customerUsername);

        if (optionalCustomer.isEmpty())
            return showSupportServiceError(model, "The support service is currently unavailable, please try again later.");

        Customer customer = optionalCustomer.get();

        Chat chat = supportService.getChatWithUserByUsername(customer.getUsername());
        if (chat == null)
            return showSupportServiceError(model, "The support service is currently unavailable, please try again later.");

        message.setChat(chat);
        // minusSeconds(5) necessary because of "past or present"-constraint at the target system
        message.setTimestamp(LocalDateTime.now().minusSeconds(5));

        boolean sent;

        try {
            sent = supportService.sendMessage(message);
        } catch (Exception ex) {
            return showSupportServiceError(model, "The support service is currently unavailable, please try again later.");
        }

        if (!sent)
            return showSupportServiceError(model, "The support service is currently unavailable, please try again later.");

        if (authentication.getPrincipal() instanceof Employee)
            return "redirect:/support/" + customer.getId();
        else
            return "redirect:/support";
    }

    @GetMapping("/{cid}")
    public String getSupportChatViewForEmployee(Model model, @PathVariable("cid") long cId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Employee employee = employeeService.getEmployeeByUsername(currentUserName).get();
        Customer customer = employee.getActiveCustomers().stream()
                .filter(c -> cId == c.getId())
                .findAny()
                .orElse(null);
        if (customer != null) {
            return getMessagesForUser(model, authentication, customer.getUsername());

        }

        return showSupportServiceError(model, "Error trying to get chat between customer and employee.");
    }

    private String getMessagesForUser(Model model, Authentication authentication, String username) {
        Chat chat = supportService.getChatWithUserByUsername(username);
        if (chat == null)
            return showSupportServiceError(model, "Chat cannot be loaded from associate system.");

        List<Message> messages = (List<Message>) supportService.pullMessages(chat, creationTime);
        if (messages == null)
            return showSupportServiceError(model, "Chat cannot be loaded from associate system.");


        model.addAttribute("messages", messages);
        model.addAttribute("username", username);

        Message message = new Message();

        message.setChat(chat);
        model.addAttribute("message", new Message());

        if (authentication.getPrincipal() instanceof Employee) {
            model.addAttribute("isEmployee", true);

            Optional<Employee> employee = employeeService.getEmployeeByUsername(((Employee) authentication.getPrincipal()).getUsername());

            if (employee != null)
                model.addAttribute("customers", employee.get().getActiveCustomers());

            return "employee/supportChat.html";
        }


        model.addAttribute("isEmployee", false);
        return "customer/supportChat.html";
    }

    private String showSupportServiceError(Model model, String message) {
        WebsiteMessage msg = new WebsiteMessage(WebsiteMessageType.Danger, "Support Service Error", message);
        model.addAttribute("message", msg);
        return "messages";
    }

}
