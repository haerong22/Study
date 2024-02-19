package Observer.example03;

public class Button {

    private Observable<String> observable;

    public Button() {
        observable = new Observable<>();
    }

    public void addObserver(Observable.Observer<String> o) {
        observable.addObserver(o);
    }

    public void notifyObservers() {
        observable.notifyObservers(null);
    }

    public void onClick() {
        observable.setChanged();
        notifyObservers();
    }
}
