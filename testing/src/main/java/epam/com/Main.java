package epam.com;

import epam.com.generator.TemplateGenerator;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<String> params = List.of(args);

    TemplateGenerator templateGenerator = new TemplateGenerator(System.console());

    templateGenerator.createTemplate(params);
  }
}