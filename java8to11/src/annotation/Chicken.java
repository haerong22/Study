package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE_PARAMETER) // type_parameter : 제네릭 타입에 @ 사용
@Target(ElementType.TYPE_USE) // type_use : 타입을 사용하는 모든 곳에서 사용가능
@Repeatable(ChickenContainer.class) // 어노테이션 중복 사용할 컨테이너 어노테이션 필요
public @interface Chicken {
    String value();
}
