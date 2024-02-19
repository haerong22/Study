package AbstractFactory.example01.gt;

import AbstractFactory.example01.abst.BikeFactory;
import AbstractFactory.example01.abst.Body;
import AbstractFactory.example01.abst.Wheel;

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
