package factory_method.before;

public class Client {

    public static void main(String[] args) {
        Ship whiteShip = ShipFactory.orderShip("WhiteShip", "keesun@mail.com");
        System.out.println(whiteShip);

        Ship blackShip = ShipFactory.orderShip("BlackShip", "keesun@mail.com");
        System.out.println(blackShip);
    }

}
