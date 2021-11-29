package abstract_factory.before;

import factory_method.after.DefaultShipFactory;
import factory_method.after.Ship;
import factory_method.after.WhiteShip;

public class WhiteShipFactory extends DefaultShipFactory {
    @Override
    public Ship createShip(String name) {
        Ship ship = new WhiteShip("white ship");
        ship.setAnchor(new WhiteAnchor());
        ship.setWheel(new WhiteWheel());
        return ship;
    }
}
