package jmp.reactive.client;

import jmp.reactive.model.User;
import jmp.reactive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserClient {

  private final WebClient webClient;
  private final UserRepository userRepository;

  public Flux<User> getUsers() {
    return webClient.get().uri(uri -> uri.path("/users").build())
        .exchangeToFlux(resp -> resp.bodyToFlux(User.class))
        .doOnError(err -> log.error("Error getting response", err));
  }
}
