package service.impl;


import dto.BankCard;
import dto.Subscription;
import dto.User;
import repository.Repository;
import repository.impl.RepositoryImpl;
import service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ServiceImpl implements Service {

  private final Repository repository;

  public ServiceImpl() {
    repository = new RepositoryImpl();
  }

  @Override
  public void subscribe(BankCard card) {
    var subscription = new Subscription(card.getNumber(), LocalDate.now());
    repository.addSubscription(subscription);
  }

  @Override
  public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
    return repository.getSubscriptionByCardNumber(cardNumber);
  }

  @Override
  public List<User> getAllUsers() {
    return repository.getAllUsers();
  }

  @Override
  public double getAverageUsersAge() {
    return getAllUsers().stream()
        .mapToLong(User::getAge)
        .summaryStatistics().getAverage();
  }

  @Override
  public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
    return repository.getAllSubscription()
        .stream().filter(predicate).toList();
  }
}
