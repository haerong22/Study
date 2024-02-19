package Facade;

import Facade.system.Facade;

public class Application {

    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.process();
    }
}
