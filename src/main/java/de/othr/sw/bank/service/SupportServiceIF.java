package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Message;

import java.util.List;

public interface SupportServiceIF {
    List<Message> pullMessages(String token);
    boolean sendMessage(Message message, Message.User user, String token);
    Message.User findUserByUsername(String username, String token);
}
