package FactoryMethod.after;

public interface ShipFactory {

    default Ship orderShip(String name, String email) {
        validate(name, email);
        prepareFor(name);
        Ship ship = createShip(name);
        sendEmailTo(email, ship);
        return ship;
    }

    Ship createShip(String name);

    void validate(String name, String email);

    void prepareFor(String name);

    void sendEmailTo(String email, Ship ship);
}
