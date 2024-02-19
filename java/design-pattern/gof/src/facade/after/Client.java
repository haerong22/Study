package facade.after;

public class Client {

    public static void main(String[] args) {
        EmailSettings emailSettings = new EmailSettings();
        emailSettings.setHost("127.0.0.1");

        EmailSender emailSender = new EmailSender(emailSettings);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFrom("kim");
        emailMessage.setTo("lee");
        emailMessage.setSubject("오징어게임");
        emailMessage.setText("밖은 더 지옥이더라고...");

        emailSender.sendEmail(emailMessage);
    }

}
