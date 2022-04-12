package com.example.refactoring._21_alternative_classes_with_different_interfaces;

public class Notification {

    private String title;
    private String receiver;
    private String sender;

    private Notification(String title) {
        this.title = title;
    }

    public static Notification newNotification(String title) {
        return new Notification(title);
    }

    public Notification receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public Notification sender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
