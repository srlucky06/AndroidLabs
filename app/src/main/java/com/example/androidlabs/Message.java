package com.example.androidlabs;


public class Message {
    private String message;
    private Boolean isReceivedMessage;
    private long databaseId;

    public Message(String message, Boolean isReceivedMessage) {
        this(message, isReceivedMessage,0);
    }


    public Message(String msg, Boolean isMsgReceived,long dId) {
        message = msg;
        isReceivedMessage= isMsgReceived;
        databaseId=dId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceivedMessage(Boolean receivedMessage) {
        isReceivedMessage = receivedMessage;
    }

    public void setDatabaseId(Long databaseId) { this.databaseId = databaseId;
    }
    public String getMessage() {
        return message;
    }

    public Boolean getReceivedMessage() {
        return isReceivedMessage;
    }

    public Long getDatabaseId() { return databaseId; }
}
