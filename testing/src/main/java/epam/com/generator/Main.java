package epam.com.generator;

import epam.com.generator.generator.TemplateGenerator;

public class Main {

  public static void main(String[] args) {

    TemplateGenerator templateGenerator = new TemplateGenerator(System.console());

    templateGenerator.createTemplate(args);
  }
}