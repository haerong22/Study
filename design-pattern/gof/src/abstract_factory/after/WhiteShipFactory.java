package abstract_factory.after;

import factory_method.after.DefaultShipFactory;
import factory_method.after.Ship;
import factory_method.after.WhiteShip;

public class WhiteShipFactory extends DefaultShipFactory {

    private final ShipPartsFactory shipPartsFactory;

    public WhiteShipFactory(ShipPartsFactory shipPartsFactory) {
        this.shipPartsFactory = shipPartsFactory;
    }

    @Override
    public Ship createShip(String name) {
        Ship ship = new WhiteShip(name);
        ship.setAnchor(shipPartsFactory.createAnchor());
        ship.setWheel(shipPartsFactory.createWheel());
        return ship;
    }
}
