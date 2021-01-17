package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
// todo implement if of associate project
public class SupportService implements SupportServiceIF {
    // todo implement exception handling for api calls
    private String baseUrl = "im-codd:8941/api/";

    @Override
    public List<Message> pullMessages(String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        // todo adjust path
        String path = baseUrl+"messages";

        ResponseEntity<Message[]> responseEntity = restTemplate.getForEntity(path,Message[].class);

        if(responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to get conversation between the participants.");

        if(responseEntity.hasBody()){
            List<Message> messages = Arrays.asList(responseEntity.getBody());
            return messages;
        }

        return new LinkedList<>();
    }

    @Override
    public boolean sendMessage(Message message, Message.User user, String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        // todo adjust path
        String path = baseUrl+"messages/new";

        ResponseEntity<Message[]> responseEntity = restTemplate.postForEntity(path,message,Message[].class);

        if(responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to send message.");

        // todo adjust HTTP Status Code and Error handling
        if(responseEntity.getStatusCode() == HttpStatus.CREATED)
            return true;
        else
            return false;
    }

    @Override
    public Message.User findUserByUsername(String username, String token) throws SupportServiceException {
        RestTemplate restTemplate = new RestTemplate();
        // todo adjust path
        String path = baseUrl+"users/"+username;

        ResponseEntity<Message.User> responseEntity = restTemplate.getForEntity(path,Message.User.class);

        if(responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError())
            throw new SupportServiceException("Error trying to find user.");

        // todo adjust HTTP Status Code and Error handling
        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody())
            return responseEntity.getBody();
        else
            throw new SupportServiceException("Error interpreting found user from support service.");
    }
}
