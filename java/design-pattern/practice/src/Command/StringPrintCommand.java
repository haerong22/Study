package Command;

public class StringPrintCommand implements Command{

    private final String string;

    public StringPrintCommand(String string) {
        this.string = string;
    }

    @Override
    public void execute() {
        System.out.println(this.string);
    }

    @Override
    public int compareTo(Command o) {
        return this.string.length() - ((StringPrintCommand) o).string.length();
    }

}
