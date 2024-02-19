package com.example.refactoring._21_alternative_classes_with_different_interfaces;

public class OrderAlerts {

    private NotificationService notificationService;

    public OrderAlerts(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void alertShipped(Order order) {
        Notification notification = Notification.newNotification(order.toString() + " is shipped")
                .receiver(order.getEmail());
        notificationService.sendNotification(notification);
    }
}
