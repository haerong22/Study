package AbstractFactory.example02.concrete;

import AbstractFactory.example02.abst.Button;
import AbstractFactory.example02.abst.GuiFactory;
import AbstractFactory.example02.abst.TextArea;

public class FactoryInstance {

    public static GuiFactory getGuiFactory() {

        switch (getOsCode()) {
            case 0: return new MacGuiFactory();
            case 1: return new WinGuiFactory();
            case 2: return new LinuxGuiFactory();
        }

        return null;
    }

    private static int getOsCode() {
        if (System.getProperty("os.name").equals("Windows 10")){
            return 1;
        }
        return 2;
    }
}
class LinuxButton implements Button {
    @Override
    public void click() {
        System.out.println("Linux Button");
    }
}
class LinuxGuiFactory implements GuiFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public TextArea createTextArea() {
        return new LinuxTextArea();
    }
}
class LinuxTextArea implements TextArea {
    @Override
    public String getText() {
        return "Linux TextArea";
    }
}
class MacButton implements Button {
    @Override
    public void click() {
        System.out.println("Mac button");
    }
}
class MacGuiFactory implements GuiFactory {
    @Override
    public Button createButton() {
        return new MacButton();

    }

    @Override
    public TextArea createTextArea() {
        return new MacTextArea();
    }
}
class MacTextArea implements TextArea {
    @Override
    public String getText() {
        return "Mac TextArea";
    }
}
class WinButton implements Button {
    @Override
    public void click() {
        System.out.println("Win button");
    }
}
class WinGuiFactory implements GuiFactory {
    @Override
    public Button createButton() {
        return new WinButton();

    }

    @Override
    public TextArea createTextArea() {
        return new WinTextArea();
    }
}
class WinTextArea implements TextArea {
    @Override
    public String getText() {
        return "Win TextArea";
    }
}