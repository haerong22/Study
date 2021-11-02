package Facade.system;

public class Facade {

    private HelpSystem01 helpSystem01;
    private HelpSystem02 helpSystem02;
    private HelpSystem03 helpSystem03;

    public Facade() {
        helpSystem01 = new HelpSystem01();
        helpSystem02 = new HelpSystem02();
        helpSystem03 = new HelpSystem03();
    }

    public void process() {
        helpSystem01.process();
        helpSystem02.process();
        helpSystem03.process();
    }
}
