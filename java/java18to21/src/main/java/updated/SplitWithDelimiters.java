package updated;

public class SplitWithDelimiters {

    public static void main(String[] args) {

        String str = "A;B;C";

        // [A, ;, B, ;, C]
        String[] result = str.splitWithDelimiters(";", -1);
    }
}
