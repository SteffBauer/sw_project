package de.othr.sw.bank.associateImitation;

import java.util.Date;
import java.util.List;

// todo delete and use class of chat-service
public class Message {
    private String text;
    private Date timestamp;
    private User author;
    private List<Message> replyOf;

    public Message() {
        this.timestamp=new Date();
    }

    public Message(String text, User author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Message> getReplyOf() {
        return replyOf;
    }

    public void setReplyOf(List<Message> replyOf) {
        this.replyOf = replyOf;
    }

    public static class User{
        private String username;
        private String password;

        public User() {}

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
