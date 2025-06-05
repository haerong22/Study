package optional;

import optional.model.Address;
import optional.model.User;

public class AddressMain1 {

    public static void main(String[] args) {
        User user1 = new User("user1", null);
        User user2 = new User("user2", new Address("hello street"));

        printStreet(user1);
        printStreet(user2);
    }
    /*
        Unknown
        hello street
     */

    private static void printStreet(User user) {
        String userStreet = getUserStreet(user);
        if (userStreet != null) {
            System.out.println(userStreet);
        } else {
            System.out.println("Unknown");
        }
    }

    static String getUserStreet(User user) {
        if (user == null) {
            return null;
        }

        Address address = user.getAddress();
        if (address == null) {
            return null;
        }

        return address.getStreet();
    }
}
