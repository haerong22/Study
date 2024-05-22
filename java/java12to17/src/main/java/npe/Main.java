package npe;

public class Main {

    public static void main(String[] args) {
        User user = new User(null);

        boolean isBlank = user.name().isBlank();
    }

    record User(String name) {
    }
}
