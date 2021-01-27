package de.othr.sw.bank.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.service.IFSendMessage;
import de.othr.bib48218.chat.entity.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SupportService implements IFSendMessage {
    // todo add to application.properties
    private String baseUrl = "http://im-codd:8941/webapi/v1/";

    @Override
    public User getUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> responseEntity;
        String path = baseUrl + "persons/" + username;

        try {
            responseEntity = restTemplate.getForEntity(path, User.class);
        } catch (Exception e) {
            //throw new SupportServiceException(e.getMessage());
            return null;
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            return null;
        //throw new SupportServiceException("Error trying to find user.");

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody())
            return responseEntity.getBody();
        else
            return null;
        //throw new SupportServiceException("Error interpreting found user from support service.");
    }

    @Override
    public Chat getChatWithUserByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Chat> responseEntity;
        String path = baseUrl + "persons/" + username + "/chat";

        try {
            responseEntity = restTemplate.getForEntity(path, Chat.class);
        } catch (Exception e) {
            //throw new SupportServiceException(e.getMessage());
            return null;
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            return null;
        //throw new SupportServiceException("Error trying to find user.");

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody())
            return responseEntity.getBody();
        else
            return null;
        //throw new SupportServiceException("Error interpreting found user from support service.");
    }

    @Override
    public void sendMessage(Message message) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Message[]> responseEntity;
        String path = baseUrl + "messages";

        try {
            responseEntity = restTemplate.postForEntity(path, message, Message[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
            //throw new SupportServiceException(e.getMessage());
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            System.out.println(responseEntity.getStatusCode().value() + " - " + responseEntity.getStatusCode().getReasonPhrase());
            return;
            //throw new SupportServiceException("Error trying to send message.");
        }

    }

    @Override
    public Collection<Message> pullMessages(Chat chat, LocalDateTime dateTime) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Message[]> responseEntity;

        String path = baseUrl + "messages/" + chat.getId() + "?since=" + dateTime;

        try {
            responseEntity = restTemplate.getForEntity(path, Message[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
            //throw new SupportServiceException(e.getMessage());
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            System.out.println(responseEntity.getStatusCode().value() + " - " + responseEntity.getStatusCode().getReasonPhrase());
            return null;
            //throw new SupportServiceException("Error trying to get conversation between the participants.");
        }

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            List<Message> messages = Arrays.asList(responseEntity.getBody());
            return messages;
        }

        return new LinkedList<>();
    }

}
