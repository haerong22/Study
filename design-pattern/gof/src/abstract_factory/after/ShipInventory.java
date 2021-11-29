package abstract_factory.after;

import factory_method.after.Ship;
import factory_method.after.ShipFactory;

public class ShipInventory {

    public static void main(String[] args) {
        ShipFactory shipFactory = new WhiteShipFactory(new WhiteShipPartsFactory());
        Ship ship = shipFactory.createShip("white ship");
        System.out.println("ship.getAnchor() = " + ship.getAnchor());
        System.out.println("ship.getWheel() = " + ship.getWheel());
    }
}
