package hello.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class CommandLineV2 {

    // --url=devdb --username=dev --password=1234 mode=on
    public static void main(String[] args) {
        Arrays.stream(args).forEach(arg -> log.info("arg = {}", arg));

        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs()));
        log.info("NonOptionsArgs = {}", appArgs.getNonOptionArgs());
        log.info("OptionsNames = {}", appArgs.getOptionNames());

        Set<String> optionNames = appArgs.getOptionNames();
        optionNames.forEach(
                optionName -> log.info("option arg {}={}", optionName, appArgs.getOptionValues(optionName))
        );

        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> mode = appArgs.getOptionValues("mode");
        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);
        log.info("mode={}", mode);

    }
}
