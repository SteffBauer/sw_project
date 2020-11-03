package de.othr.sw.bank.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue
    private long messageId;
    private Boolean success;
    private String shortText;
    private String longText;

    public Message() { }

    public Message(Boolean success, String shortText, String longText) {
        this.success = success;
        this.shortText = shortText;
        this.longText = longText;
    }

    public long getMessageId() {
        return messageId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(Message.class))
            return false;
        Message other=(Message)o;

        return this.messageId==other.messageId;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.messageId);
    }

}
