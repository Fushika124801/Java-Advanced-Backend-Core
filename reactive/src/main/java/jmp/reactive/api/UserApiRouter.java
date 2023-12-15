package jmp.reactive.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserApiRouter {

  @Bean
  public RouterFunction<ServerResponse> routes(UserApiHandler handler) {
    RequestPredicate getUserByName = RequestPredicates
        .GET("/api/v1/users")
        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

    RequestPredicate saveUserRoute = RequestPredicates
        .POST("/api/v1/users/{name}")
        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

    return RouterFunctions
        .route(getUserByName, handler::getUsers)
        .andRoute(saveUserRoute, handler::saveUser);
  }


}
