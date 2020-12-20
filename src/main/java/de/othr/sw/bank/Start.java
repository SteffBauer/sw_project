package de.othr.sw.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class Start {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Start.class, args);
    }

}
