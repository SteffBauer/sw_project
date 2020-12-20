package de.othr.sw.bank.entity;

public class WebsiteMessage {
    private WebsiteMessageType messageType;
    private String title;
    private String message;

    public WebsiteMessage(){}

    public WebsiteMessage(WebsiteMessageType messageType, String title, String message) {
        this.messageType = messageType;
        this.title = title;
        this.message = message;
    }

    public WebsiteMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(WebsiteMessageType messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}



