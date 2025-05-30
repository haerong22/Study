package stream.collectors;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DownStreamMain2 {

    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("Kim", 1, 85),
                new Student("Park", 1, 70),
                new Student("Lee", 2, 70),
                new Student("Han", 2, 90),
                new Student("Hoon", 3, 90),
                new Student("Ha", 3, 89)
        );

        // 학년별로 그룹화
        Map<Integer, List<Student>> collect1 = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade));
        System.out.println("collect1 = " + collect1);

        // 학년별로 가장 점수가 높은 학생(reducing)
        Map<Integer, Optional<Student>> collect2 = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        Collectors.reducing((s1, s2) -> s1.getScore() > s2.getScore() ? s1 : s2)
                ));
        System.out.println("collect2 = " + collect2);

        // 학년별로 가장 점수가 높은 학생(maxBy)
        Map<Integer, Optional<Student>> collect3 = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
//                        Collectors.maxBy((s1, s2) -> s1.getScore() > s2.getScore() ? 1 : -1)
                        Collectors.maxBy(Comparator.comparingInt(Student::getScore))
                ));
        System.out.println("collect3 = " + collect3);

        // 학년 별로 가장 점수가 높은 학생의 이름
        Map<Integer, String> collect4 = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Student::getScore)),
                                student -> student.get().getName()
                        )
                ));
        System.out.println("collect4 = " + collect4);
    }
    /*
        collect1 = {1=[Student{name='Kim', grade=1, score=85}, Student{name='Park', grade=1, score=70}], 2=[Student{name='Lee', grade=2, score=70}, Student{name='Han', grade=2, score=90}], 3=[Student{name='Hoon', grade=3, score=90}, Student{name='Ha', grade=3, score=89}]}
        collect2 = {1=Optional[Student{name='Kim', grade=1, score=85}], 2=Optional[Student{name='Han', grade=2, score=90}], 3=Optional[Student{name='Hoon', grade=3, score=90}]}
        collect3 = {1=Optional[Student{name='Kim', grade=1, score=85}], 2=Optional[Student{name='Han', grade=2, score=90}], 3=Optional[Student{name='Hoon', grade=3, score=90}]}
        collect4 = {1=Kim, 2=Han, 3=Hoon}
     */
}
