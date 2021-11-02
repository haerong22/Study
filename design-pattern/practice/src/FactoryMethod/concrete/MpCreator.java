package FactoryMethod.concrete;

import FactoryMethod.framework.Item;
import FactoryMethod.framework.ItemCreator;

import java.time.LocalDateTime;

public class MpCreator extends ItemCreator {
    @Override
    protected void requestItemsInfo() {
        System.out.println("데이터베이스에서 마력 회복 물약의 정보 조회");
    }

    @Override
    protected void createItemLog() {
        System.out.println("마력 회복 물약을 생성 : " + LocalDateTime.now());
    }

    @Override
    protected Item createItem() {
        return new MpPotion();
    }
}
