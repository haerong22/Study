package template.after.template_method;

public class Multiply extends FileProcessor {

    public Multiply(String path) {
        super(path);
    }

    @Override
    protected int getResult(int result, int line) {
        return result * line;
    }
}
