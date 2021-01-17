package AbstractFactory.example01.sam;

import AbstractFactory.example01.abst.BikeFactory;
import AbstractFactory.example01.abst.Body;
import AbstractFactory.example01.abst.Wheel;

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
