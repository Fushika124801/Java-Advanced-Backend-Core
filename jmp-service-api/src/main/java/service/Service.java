package service;

import dto.BankCard;
import dto.Subscription;
import dto.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

  default boolean isPayableUser(User user) {
    return user.getAge() >= 18;
  }

  void subscribe(BankCard card);

  Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

  List<User> getAllUsers();

  double getAverageUsersAge();

  List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);
}
