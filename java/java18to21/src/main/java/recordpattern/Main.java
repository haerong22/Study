package recordpattern;

public class Main {

    record Point(double x, double y) {}

    record Line(Point p1, Point p2) {}

    public static void findDistanceIfPoint(Object object) {

        if (object instanceof Point(double x, double y)) {
            double dist = Math.hypot(x, y);
            System.out.printf("원점 으로부터의 거리 : %.3f\n", dist);
        }

        if (object instanceof Line(Point(var x1, var y1), Point(var x2, var y2))) {
            double dist = Math.hypot(x2 - x1, y2 - y1);
            System.out.printf("두 점 사이의 거리 : %.3f\n", dist);
        }
    }
}
