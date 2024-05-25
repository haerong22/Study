package switchpatternmatching;

public class Main {

    public String sound(Animal animal) {
        return switch (animal) {
            case Dog dog when dog.isQuiet() -> "";
            case Dog dog -> dog.bark();
            case Cat cat -> cat.purr();
        };
    }
}
