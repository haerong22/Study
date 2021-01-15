package Decorator.concrete;

import Decorator.abst.AbstAdding;
import Decorator.abst.IBeverage;

public class Espresso extends AbstAdding {

    static protected int espressoCount = 0;

    public Espresso(IBeverage base) {
        super(base);
    }

    @Override
    public int getTotalPrice() {
        return super.getTotalPrice() + getAddPrice();
    }

    private static int getAddPrice() {
        espressoCount += 1;
        int addPrice = 100;
        if (espressoCount > 1) {
            addPrice = 70;
        }
        return addPrice;
    }
}
