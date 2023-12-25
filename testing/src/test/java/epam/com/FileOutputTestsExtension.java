package epam.com;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class FileOutputTestsExtension implements AfterTestExecutionCallback {


  @Override
  public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
    try (OutputStream out = new FileOutputStream("src/test/resources/log.txt", true)) {
      String testClassName = extensionContext.getDisplayName();
      LocalDateTime now = LocalDateTime.now();
      String executionStatus =
          extensionContext.getExecutionException().isEmpty() ? "Executed" : "Failed";
      out.write(
          format("%s %s : %s\n", now.format(ISO_DATE_TIME),
              testClassName, executionStatus).getBytes());
    }
  }
}
