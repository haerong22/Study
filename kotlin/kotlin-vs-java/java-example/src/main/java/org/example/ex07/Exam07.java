package org.example.ex07;

/**
 * data class
 */
public class Exam07 {
    public Exam07() {
        User user = new User();
        user.setName("홍길동");
        user.setEmail("email@email.com");
        user.setAge(20);
    }

    public static void main(String[] args) {
        new Exam07();
    }
}
