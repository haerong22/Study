package Strategy;

public class GameCharacter {
    // 접근점
    private Weapon weapon;

    // 교환 가능
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack() {
        // 델리게이트
        if (weapon == null ) {
            System.out.println("맨손공격 퍽퍽");
        } else {
            weapon.attack();
        }
    }
}
