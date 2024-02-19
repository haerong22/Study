package Decorator.concrete;

import Decorator.abst.IBeverage;

public class Base implements IBeverage {
    @Override
    public int getTotalPrice() {
        return 0;
    }
}
