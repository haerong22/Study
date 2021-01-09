package FactoryMethod;

import FactoryMethod.concrete.HpCreator;
import FactoryMethod.concrete.MpCreator;
import FactoryMethod.framework.Item;
import FactoryMethod.framework.ItemCreator;

public class Main {

    public static void main(String[] args) {
        ItemCreator creator;
        Item item;

        creator = new HpCreator();
        item = creator.create();

        item.use();

        creator = new MpCreator();
        item = creator.create();
        item.use();
    }
}
