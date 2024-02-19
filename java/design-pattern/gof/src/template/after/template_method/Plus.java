package template.after.template_method;

import template.after.template_method.FileProcessor;

public class Plus extends FileProcessor {

    public Plus(String path) {
        super(path);
    }

    @Override
    protected int getResult(int result, int line) {
        return result + line;
    }
}
