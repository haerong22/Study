package functional;

public class ImmutablePerson {

    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge);
    }

    public ImmutablePerson withName(String newName) {
        return new ImmutablePerson(newName, this.age);
    }

    @Override
    public String toString() {
        return "ImmutablePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
