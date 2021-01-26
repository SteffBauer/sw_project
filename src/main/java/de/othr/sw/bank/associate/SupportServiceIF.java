package de.othr.sw.bank.associate;

import de.othr.sw.bank.associate.Message;
import de.othr.sw.bank.service.SupportServiceException;

import java.util.List;

// todo delete and use if of associate project
public interface SupportServiceIF {
    List<Message> pullMessages(String token) throws SupportServiceException;
    boolean sendMessage(Message message, Message.User user, String token) throws SupportServiceException;
    Message.User findUserByUsername(String username, String token) throws SupportServiceException;
}
