package lambda.lambda5.map;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GenericMapper {

    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> numbers = new ArrayList<>();
        for (T s : list) {
            numbers.add(mapper.apply(s));
        }
        return numbers;
    }
}
