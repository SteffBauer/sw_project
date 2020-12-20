package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.service.EmployeeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CustomerServiceIF customerServiceIF;

    @Autowired
    EmployeeServiceIF employeeService;

    @RequestMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "index";


    }

    @RequestMapping("/dashboard")
    public String showDashboard(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "info", required = false) String info) {
        model.addAttribute("today", new Date().toString());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        // Handle notifications for the dashboard
        if (error != null)
            model.addAttribute("error", error);
        else if(info != null)
            model.addAttribute("info", info);

        // Logged in user is type of customer
        if(authentication.getPrincipal() instanceof Customer) {
            Customer customer = (Customer) authentication.getPrincipal();
            model.addAttribute("customer",true);
            model.addAttribute("name", customer.getForename());

            List<Account> accounts = customerServiceIF.getAccountsForUser(customer.getId());
            model.addAttribute("accounts", accounts);

            long sum = 0L;
            for (Account account : accounts) {
                sum += account.getBalance();
            }

            model.addAttribute("totalBalance", sum);
        }
        // Logged in user is type of employee
        else if(authentication.getPrincipal() instanceof Employee){
            Employee employee = (Employee) authentication.getPrincipal();
            // Update Employee in case of deleted customers
            employee =employeeService.getEmployeeByUsername(employee.getUsername()).get();


            model.addAttribute("customer",false);
            model.addAttribute("name", employee.getForename());

            List<Customer> customers = employee.getCustomers();
            model.addAttribute("customers", customers);
        }

        return "/dashboard";

    }


}
