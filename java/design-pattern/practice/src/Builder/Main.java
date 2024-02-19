package Builder;

public class Main {

    public static void main(String[] args) {

        ComputerFactory factory = new ComputerFactory();
        factory.setBlueprint(new LgGramBlueprint());
        Computer computer = factory.makeAndGet();

        System.out.println(computer.toString());
    }
}
