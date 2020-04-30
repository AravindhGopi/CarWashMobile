package com.tts.carwash.model;

public class SMSModel {

    public int getBalance() {
        return balance;
    }

    public int getBatch_id() {
        return batch_id;
    }

    public int getCost() {
        return cost;
    }

    public int getNum_messages() {
        return num_messages;
    }

    public String getMessage() {
        return message;
    }

    public String getReceipt_url() {
        return receipt_url;
    }

    public String getCustom() {
        return custom;
    }

    public String getMessages() {
        return messages;
    }

    public String getStatus() {
        return status;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNum_messages(int num_messages) {
        this.num_messages = num_messages;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceipt_url(String receipt_url) {
        this.receipt_url = receipt_url;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int balance;
    private int batch_id;
    private int cost;
    private int num_messages;
    private String message;
    private String receipt_url;
    private String custom;
    private String messages;
    private String status;
}
