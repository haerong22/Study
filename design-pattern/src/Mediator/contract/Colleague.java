package Mediator.contract;

public abstract class Colleague {

    private Mediator mediator;

    public boolean join(Mediator mediator) {
        if(mediator == null) return false;
        this.mediator = mediator;
        return mediator.addColleague(this);
    }

    public void sendData(String data) {
        if(mediator != null) {
            mediator.mediate(data);
        }
    }

    public abstract void handle(String data);
}
