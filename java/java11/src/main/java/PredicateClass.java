import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateClass {

    private void not() {
        List<String> strings = List.of("A", " ", "  ");

        List<String> result = strings.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());
    }
}
