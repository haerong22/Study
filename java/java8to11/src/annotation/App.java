package annotation;

import java.util.Arrays;
import java.util.List;


@Chicken("양념")
@Chicken("후라이드")
@Chicken("간장")
public class App {

    public static void main(@Pizza String[] args) throws @Pizza RuntimeException {
        List<@Pizza String> names = Arrays.asList("hello");
        Chicken[] chickens = App.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens).forEach(c -> System.out.println(c.value()));

        System.out.println("========================================================");

        ChickenContainer chickenContainer = App.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value()).forEach(c -> System.out.println(c.value()));
    }

    static class FeelsLikeChicken<@Pizza T> {

        public static <@Pizza C> void print(@Pizza C c) {

        }
    }
}
