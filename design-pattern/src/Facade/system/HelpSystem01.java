package Facade.system;

class HelpSystem01 {

    public HelpSystem01() {
        System.out.println("Call Constructor : " + getClass().getName());
    }

    public void process() {
        System.out.println("Call Process : " + getClass().getSimpleName());
    }
}
