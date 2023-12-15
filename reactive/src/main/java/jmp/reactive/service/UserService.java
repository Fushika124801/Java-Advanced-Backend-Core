package jmp.reactive.service;

import jmp.reactive.client.UserClient;
import jmp.reactive.model.User;
import jmp.reactive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscription;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;
import reactor.core.publisher.BaseSubscriber;

@Service
@RequiredArgsConstructor
public class UserService implements SmartLifecycle {

  private final UserRepository repository;
  private final UserClient userClient;


  @Override
  public void start() {
    userClient.getUsers()
        .log()
        .subscribe(new BaseSubscriber<User>() {
          private int count = 0;
          private int limit = 20;

          @Override
          protected void hookOnSubscribe(Subscription subscription) {
            request(limit);
          }

          @Override
          protected void hookOnNext(User user) {
            repository.save(user).subscribe();
            if (++count % limit == 0) {
              request(limit);
            }
          }
        });
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }
}
