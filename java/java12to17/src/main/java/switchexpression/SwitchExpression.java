package switchexpression;

public class SwitchExpression {

    private String calculateGradeBefore(int score) {
        String grade = "";

        switch (score) {
            case 5:
                grade = "A";
                break;
            case 4:
            case 3:
                grade = "B";
                break;
            case 2:
            case 1:
                grade = "C";
                break;
            default:
                grade = "F";
        }

        return grade;
    }

    private String calculateGradeAfter(int score) {
        return switch (score) {
            case 5:
                yield "A";
            case 4, 3:
                yield "B";
            case 2, 1:
                yield "C";
            default:
                yield "F";
        };
    }

    private String calculateGradeAfter2(int score) {
        return switch (score) {
            case 5 -> "A";
            case 4, 3 -> "B";
            case 2, 1 -> "C";
            default -> "F";
        };
    }
}
