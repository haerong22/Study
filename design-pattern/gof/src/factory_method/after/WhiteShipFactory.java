package factory_method.after;

public class WhiteShipFactory extends DefaultShipFactory {

    @Override
    public Ship createShip(String name) {
        return new WhiteShip(name);
    }
}
