package jmp.reactive.api;

import jmp.reactive.model.User;
import jmp.reactive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserApiHandler {

  private final UserRepository repository;

  public Mono<ServerResponse> getUsers(ServerRequest request) {
    Optional<String> queryParam = request.queryParam("q");
    if (queryParam.isEmpty()) {
      return ServerResponse
          .badRequest()
          .bodyValue("QueryParam is missing");
    }

    return repository.findByLogin(queryParam.get())
        .flatMap(it ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(it)
        )
        .switchIfEmpty(ServerResponse.notFound().build());

  }

  public Mono<ServerResponse> saveUser(ServerRequest request) {
    String name = request.pathVariable("name");

    return repository.findByLogin(name)
        .flatMap(it ->
            ServerResponse
                .badRequest()
                .bodyValue(String.format("User with name '%s' exists", name))
        )
        .switchIfEmpty(
            repository.save(new User(name))
                .flatMap(it ->
                    ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("saved")
                )
        );
  }
}
