package jmp.reactive.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ClientConfiguration {

  @Bean
  public WebClient webClient(ClientProperties properties) {
    return WebClient.builder()
        .baseUrl(properties.getBaseUrl())
        .clientConnector(new
            ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection())))
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
