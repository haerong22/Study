package AbstractFactory.sam;

import AbstractFactory.abst.BikeFactory;
import AbstractFactory.abst.Body;
import AbstractFactory.abst.Wheel;

public class SamFactory implements BikeFactory {
    @Override
    public Body createBody() {
        return new SamBody();
    }

    @Override
    public Wheel createWheel() {
        return new SamWheel();
    }
}
