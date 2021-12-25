package template.after.template_method;

public class Client {

    public static void main(String[] args) {
        FileProcessor fileProcessor = new Plus("number.txt");
        System.out.println(fileProcessor.process());

        fileProcessor = new Multiply("number.txt");
        System.out.println(fileProcessor.process());
    }
}
