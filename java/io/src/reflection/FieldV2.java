package reflection;

import reflection.data.User;

import java.lang.reflect.Field;

public class FieldV2 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        User user = new User("id1", "userA", 20);
        System.out.println("기존 이름 = " + user.getName());

        Class<? extends User> aClass = user.getClass();
        Field nameField = aClass.getDeclaredField("name");

        // private 필드에 접근 허용
        nameField.setAccessible(true);
        nameField.set(user, "userB");
        System.out.println("변경된 이름 = " + user.getName());
    }

    /*
        기존 이름 = userA
        변경된 이름 = userB
     */
}
