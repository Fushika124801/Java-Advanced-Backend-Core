package epam.com.generator;

import epam.com.generator.generator.TemplateGenerator;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<String> params = List.of(args);

    TemplateGenerator templateGenerator = new TemplateGenerator(System.console());

    templateGenerator.createTemplate(params);
  }
}