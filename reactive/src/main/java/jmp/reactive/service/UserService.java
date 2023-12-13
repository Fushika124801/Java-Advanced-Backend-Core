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
        .subscribe(
            new BaseSubscriber<User>() {

              int processed;
              final int limit = 20;

              @Override
              protected void hookOnSubscribe(Subscription subscription) {
                subscription.request(limit);
              }

              @Override
              protected void hookOnNext(User value) {
                if (++processed >= limit) {
                  processed = 0;
                  repository.save(value);
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
