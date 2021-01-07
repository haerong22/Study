package Strategy;

public class Main {
    public static void main(String[] args) {
//        Ainterface ainterface = new AinterfaceImpl();
//        ainterface.funcA();
//
//        AObj aObj = new AObj();
//        aObj.funcAA();
        GameCharacter gameCharacter = new GameCharacter();

        gameCharacter.attack();

        gameCharacter.setWeapon(new Gun());
        gameCharacter.attack();

        gameCharacter.setWeapon(new Sword());
        gameCharacter.attack();

        gameCharacter.setWeapon(new Ax());
        gameCharacter.attack();
    }
}
