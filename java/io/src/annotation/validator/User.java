package annotation.validator;

public class User {

    @NotEmpty(message = "이름이 비었습니다.")
    private String name;
    @Range(min = 1, max = 100, message = "나이는 1과 100사이어야 합니다.")
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
