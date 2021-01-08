package TemplateMethod;

import TemplateMethod.connection.AbstGameConnectHelper;
import TemplateMethod.connection.DefaultGameConnectHelper;

public class Main {

    public static void main(String[] args) {
        AbstGameConnectHelper helper = new DefaultGameConnectHelper();

        helper.requestConnection("아이디 암호 등 접속 정보");
    }
}
