package preview;

/**
 * 사용하지 않은 패턴 매칭, 변수를 _로 변경
 */
public class UnnamedVariable {

    record Point(double x, double y) {
    }

    record Line(Point p1, Point p2) {
    }

    public static void findDistance(Object o) {

        if (o instanceof Line(Point(var x1, var _), Point(var x2, _))) {

        }
    }

}
