package state.after;

public class Client {

    public static void main(String[] args) {
        OnlineCourse onlineCourse = new OnlineCourse();
        Student kim = new Student("kim");

        onlineCourse.addStudent(kim);
        onlineCourse.addReview("hello", kim);

        onlineCourse.changeState(new Private(onlineCourse));
        onlineCourse.addReview("hello", kim);

        Student lee = new Student("lee");
        onlineCourse.addStudent(lee);

        lee.addPrivate(onlineCourse);
        onlineCourse.addStudent(lee);

        System.out.println(onlineCourse);
    }
}
