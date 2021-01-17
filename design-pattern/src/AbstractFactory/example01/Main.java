package AbstractFactory.example01;

import AbstractFactory.example01.abst.BikeFactory;
import AbstractFactory.example01.abst.Body;
import AbstractFactory.example01.abst.Wheel;
import AbstractFactory.example01.gt.GtBikeFactory;

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
