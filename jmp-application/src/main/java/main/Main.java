package main;

import bank.Bank;
import dto.BankCard;
import dto.BankCardType;
import dto.User;
import exception.NotFoundSubscription;
import service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ServiceLoader;

public class Main {

  public static void main(String[] args) {
    Bank bank = ServiceLoader.load(Bank.class).findFirst().get();
    Service service = ServiceLoader.load(Service.class).findFirst().get();
    List<User> users = service.getAllUsers();
    User user = users.get(0);
    BankCard bankCard = bank.createBankCard(user, BankCardType.DEBIT);
    service.subscribe(bankCard);
    System.out.println(users);
    System.out.println("Average user's age " + service.getAverageUsersAge());
    System.out.println(service.isPayableUser(user) ? "User is payable" : "User is not payable");
    var subscriptionByBankCardNumber = service.getSubscriptionByBankCardNumber(bankCard.getNumber())
        .orElseThrow(() -> new NotFoundSubscription("Not found subscription"));
    System.out.println("Subscription by card number " + subscriptionByBankCardNumber);
    var allSubscriptionsByCondition = service.getAllSubscriptionsByCondition(
        subscription -> subscription.getStartDate().isAfter(LocalDate.of(2000, 10, 10)));
    System.out.println("Subscriptions by condition " + allSubscriptionsByCondition);
  }
}
