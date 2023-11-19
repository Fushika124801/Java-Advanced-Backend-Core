package repository;

import dto.Subscription;
import dto.User;

import java.util.List;
import java.util.Optional;

public interface Repository {

  List<User> getAllUsers();

  void addSubscription(Subscription subscription);

  Optional<Subscription> getSubscriptionByCardNumber(String cardNumber);

  List<Subscription> getAllSubscription();

}
