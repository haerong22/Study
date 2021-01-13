package AbstractFactory.gt;

import AbstractFactory.abst.BikeFactory;
import AbstractFactory.abst.Body;
import AbstractFactory.abst.Wheel;

public class GtBikeFactory implements BikeFactory {
    @Override
    public Body createBody() {
        return new GtBody();
    }

    @Override
    public Wheel createWheel() {
        return new GtWheel();
    }
}
