package AbstractFactory.example02;

import AbstractFactory.example02.abst.Button;
import AbstractFactory.example02.abst.GuiFactory;
import AbstractFactory.example02.abst.TextArea;
import AbstractFactory.example02.concrete.FactoryInstance;

public class Main {
    public static void main(String[] args) {
        GuiFactory factory = FactoryInstance.getGuiFactory();

        Button button = factory.createButton();
        TextArea textArea = factory.createTextArea();

        button.click();
        System.out.println(textArea.getText());
    }
}
