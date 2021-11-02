package Proxy;

public class Proxy implements Subject{

    private final Subject realSubject;

    public Proxy(Subject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void action1() {
        System.out.println("간단한 업무 by proxy");
    }

    @Override
    public void action2() {
        this.realSubject.action2();
    }
}
