package Command;

import java.util.PriorityQueue;

public class Application {

    public static void main(String[] args) {

        PriorityQueue<Command> queue = new PriorityQueue<>();

        queue.add(new StringPrintCommand("A"));
        queue.add(new StringPrintCommand("AB"));
        queue.add(new StringPrintCommand("ABC"));
        queue.add(new StringPrintCommand("ABCD"));

        for (Command command : queue) {
            command.execute();
        }
    }
}
