package com.example.refactoring._21_alternative_classes_with_different_interfaces._before;

public class OrderProcessor {

    private EmailService emailService;

    public void notifyShipping(Shipping shipping) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTitle(shipping.getOrder() + " is shipped");
        emailMessage.setTo(shipping.getEmail());
        emailMessage.setFrom("no-reply@email.com");
        emailService.sendEmail(emailMessage);
    }

}
