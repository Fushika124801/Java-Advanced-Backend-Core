package epam.com.generator;


import epam.com.generator.generator.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Console;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateGeneratorTest {

  @InjectMocks
  private TemplateGenerator templateGenerator;

  @Mock
  private Console console;

  @Spy
  private List<String> emptyParams;

  @BeforeEach
  public void init() {
    when(emptyParams.isEmpty()).thenReturn(true);
  }

  @Test
  @Tag("ConsoleModeTest")
  @DisabledIf("java.lang.System.getProperty('os.name').toLowerCase().contains('mac')")
  void createTemplateInConsoleMode_Ok() {
    String expected = "I wrote you John because Happy New Year";
    when(console.readLine()).thenReturn("title=Happy New Year,who=John");

    String actual = templateGenerator.createTemplate(emptyParams);

    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @Tag("ConsoleModeTest")
  @ValueSource(strings = {"", "title=Happy New Year", "who=John", "sawr"})
  void createTemplateInConsoleMode_ProvidedWrongPlaceholderValue_ThrowsException(String input) {
    when(console.readLine()).thenReturn(input);

    assertThrows(IllegalArgumentException.class,
        () -> templateGenerator.createTemplate(emptyParams));
  }

  @TestFactory
  @Tag("FileModeTest")
  Stream<DynamicTest> createTemplateInFileMode_Ok() {
    List<List<String>> inuputs = List.of(List.of("title=Happy New Year", "who=John"),
        List.of("who=John", "title=Happy New Year"),
        List.of("title=Happy New Year", "who=John", "what=test"));
    String expected = "I wrote you John because Happy New Year";

    return inuputs.stream().map(
        inuput -> DynamicTest.dynamicTest("CreateTemplate with params:" + inuput,
            () -> assertEquals(expected, templateGenerator.createTemplate(inuput))));

  }
}