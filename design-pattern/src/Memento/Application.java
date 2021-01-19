package Memento;

import Memento.abc.Memento;
import Memento.abc.Originator;

import java.util.Stack;

public class Application {
    public static void main(String[] args) {

        Stack<Memento> mementos = new Stack<>();

        Originator originator = new Originator();
        originator.setState("state 1");
        mementos.push(originator.createMemento());
        originator.setState("state 2");
        mementos.push(originator.createMemento());
        originator.setState("state 3");
        mementos.push(originator.createMemento());
        originator.setState("state Final");
        mementos.push(originator.createMemento());

        originator.restoreMemento(mementos.pop());
        System.out.println(originator.getState());
        originator.restoreMemento(mementos.pop());
        System.out.println(originator.getState());
        originator.restoreMemento(mementos.pop());
        System.out.println(originator.getState());
        originator.restoreMemento(mementos.pop());
        System.out.println(originator.getState());
    }
}
