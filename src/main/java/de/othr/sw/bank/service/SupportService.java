package de.othr.sw.bank.service;

import de.othr.sw.bank.associate.Message;
import de.othr.sw.bank.associate.SupportServiceIF;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
// todo implement if of associate project
public class SupportService implements SupportServiceIF {
    // todo test exception handling for api calls

    // todo add to application.properties
    private String baseUrl = "http://im-codd:8941/api/";
    // todo date to get messages from
    private Date creationTime;

    public SupportService() {
        creationTime = new Date();
    }


    @Override
    public List<Message> pullMessages(String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Message[]> responseEntity;
        // todo adjust path
        String path = baseUrl + "messages";

        try {
            responseEntity = restTemplate.getForEntity(path, Message[].class);
        } catch (Exception e) {
            throw new SupportServiceException(e.getMessage());
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to get conversation between the participants.");

        if (responseEntity.hasBody()) {
            List<Message> messages = Arrays.asList(responseEntity.getBody());
            return messages;
        }

        return new LinkedList<>();
    }

    @Override
    public boolean sendMessage(Message message, Message.User user, String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Message[]> responseEntity;
        // todo adjust path
        String path = baseUrl + "messages/new";

        try {
            responseEntity = restTemplate.postForEntity(path, message, Message[].class);
        } catch (Exception e) {
            throw new SupportServiceException(e.getMessage());
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to send message.");

        // todo adjust HTTP Status Code and Error handling
        if (responseEntity.getStatusCode() == HttpStatus.CREATED)
            return true;
        else
            return false;
    }

    @Override
    public Message.User findUserByUsername(String username, String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Message.User> responseEntity;
        // todo adjust path
        String path = baseUrl + "users/" + username;

        try {
            responseEntity = restTemplate.getForEntity(path, Message.User.class);
        }catch (Exception e){
            throw new SupportServiceException(e.getMessage());
        }

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to find user.");

        // todo adjust HTTP Status Code and Error handling
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody())
            return responseEntity.getBody();
        else
            throw new SupportServiceException("Error interpreting found user from support service.");
    }
}
