package de.othr.sw.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Start.class, args);
    }

}
