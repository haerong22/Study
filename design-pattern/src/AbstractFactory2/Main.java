package AbstractFactory2;

import AbstractFactory2.abst.Button;
import AbstractFactory2.abst.GuiFactory;
import AbstractFactory2.abst.TextArea;
import AbstractFactory2.concrete.FactoryInstance;

public class Main {
    public static void main(String[] args) {
        GuiFactory factory = FactoryInstance.getGuiFactory();

        Button button = factory.createButton();
        TextArea textArea = factory.createTextArea();

        button.click();
        System.out.println(textArea.getText());
    }
}
