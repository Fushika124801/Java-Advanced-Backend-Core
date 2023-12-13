package jmp.reactive.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "web-client")
public class ClientProperties {

  private String baseUrl;
}
