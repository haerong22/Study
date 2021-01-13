package AbstractFactory;

import AbstractFactory.abst.BikeFactory;
import AbstractFactory.abst.Body;
import AbstractFactory.abst.Wheel;
import AbstractFactory.gt.GtBikeFactory;
import AbstractFactory.sam.SamFactory;

public class Main {
    public static void main(String[] args) {
//        BikeFactory factory = new SamFactory();
        BikeFactory factory = new GtBikeFactory();
        Body body = factory.createBody();
        Wheel wheel = factory.createWheel();

        System.out.println(body.getClass());
        System.out.println(wheel.getClass());
    }
}
