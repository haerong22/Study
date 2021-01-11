package Prototype.copy;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        Cat navi = new Cat();
        navi.setName("navi");
        navi.setAge(new Age(2018, 3));

        Cat tom = navi.copy();
        tom.setName("tom");
        tom.getAge().setYear(2020);
        tom.getAge().setValue(1);

        System.out.println(navi.getName());
        System.out.println(navi.getAge().getYear());
        System.out.println(tom.getName());
        System.out.println(tom.getAge().getYear());

    }
}
