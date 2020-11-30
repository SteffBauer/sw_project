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

        Customer c = new Customer("Vorname", "Nachname", "unique_Nutzername", new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime(), "123456", "safest_password_ever");
        Address a = new Address("Straße", 5,1234,"Stadt","Land");
        c.setAddress(a);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(c);
        System.out.println(s);

        SpringApplication.run(Start.class, args);
    }

}
