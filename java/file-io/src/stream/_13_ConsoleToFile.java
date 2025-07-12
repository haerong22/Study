package stream;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _13_ConsoleToFile {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Console: Hello world!");

        PrintStream ps = new PrintStream("./tmp/test.txt");

        PrintStream origin = System.out;
        System.setOut(ps);
        System.out.println("test.txt: HELLO WORLD");
        ps.close();

        System.setOut(origin);
        System.out.println("End");
    }
    /*
        Console: Hello world!
        End

        test.txt
        test.txt: HELLO WORLD
     */
}
