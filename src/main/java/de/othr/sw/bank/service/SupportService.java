package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SupportService implements SupportServiceIF {
    // todo implement exception handling for api calls
    // todo remove
    List<Message> messageList;

    @Override
    public List<Message> pullMessages(String token) {
        if (messageList == null || messageList.size() <= 0) {
            messageList = new LinkedList<>();

            Message.User c = new Message.User("customer Username", "");
            Message.User e = new Message.User("employee Username", "");

            for (int i = 1; i < 8; i++) {
                Message msg = new Message();
                msg.setAuthor(i % 2 == 0 ? c : e);
                msg.setText("Chat message " + i);
                messageList.add(msg);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
        return messageList;
    }

    @Override
    public boolean sendMessage(Message message, Message.User user, String token) {
        return false;
    }

    @Override
    public Message.User findUserByUsername(String username, String token) {
        return null;
    }
}
