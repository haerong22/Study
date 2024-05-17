package instanceofpattern;

public abstract class Animal {

    public String sound(Animal animal) {

        // before
//        if (animal instanceof Dog) {
//            Dog dog = (Dog) animal;
//            return dog.bark();
//        } else if (animal instanceof Cat) {
//            Cat cat = (Cat) animal;
//            return cat.purr();
//        }

        // after
        if (animal instanceof Dog dog) {
            return dog.bark();
        } else if (animal instanceof Cat cat) {
            return cat.purr();
        }

        throw new IllegalArgumentException();
    }
}
