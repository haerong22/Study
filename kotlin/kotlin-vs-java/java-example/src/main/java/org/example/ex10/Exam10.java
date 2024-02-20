package org.example.ex10;

public class Exam10 {

    public Exam10(ExamUser examUser) {

        if (examUser != null && examUser.getName() != null) {

            if (StringUtils.isNotBlank(examUser.getName())) {
                System.out.println(examUser.getName());
            }
        }

    }

    public static void main(String[] args) {
        new Exam10(new ExamUser());
        new Exam10(new ExamUser("abcd"));
    }
}

class StringUtils {

    public static boolean isNotBlank(String value) {
        return !value.isBlank();
    }
}

class ExamUser {
    private String name;

    public ExamUser() {
    }

    public ExamUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}