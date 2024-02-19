package com.example.refactoring._21_alternative_classes_with_different_interfaces;

public class OrderProcessor {

    private NotificationService notificationService;

    public OrderProcessor(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyShipping(Shipping shipping) {
        Notification notification = Notification.newNotification(shipping.getOrder() + " is shipped")
                .receiver(shipping.getEmail())
                .sender("no-reply@email.com");
        notificationService.sendNotification(notification);
    }

}
