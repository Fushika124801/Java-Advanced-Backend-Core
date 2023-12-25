package epam.com.generator;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class TemplateGenerator {

  private final Console console;

  public TemplateGenerator(Console console) {
    this.console = console;
  }

  public String createTemplate(List<String> params) {
    String title;
    String who;
    if (params.isEmpty()) {
      List<String> consoleParams = Arrays.stream(console.readLine()
          .split(",")).toList();
      title = getParam(consoleParams, "title");
      who = getParam(consoleParams, "who");
    } else {
      title = getParam(params, "title");
      who = getParam(params, "who");
    }

    return format("I wrote you %s because %s", who, title);
  }

  private String getParam(List<String> params, String nameParam) {
    return params.stream().filter(str -> str.contains(nameParam)).findFirst()
        .orElseThrow(IllegalArgumentException::new).split("=")[1];
  }
}
