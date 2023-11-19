package bank.impl;

import bank.Bank;
import dto.BankCard;
import dto.BankCardType;
import dto.CreditBankCard;
import dto.DebitBankCard;
import dto.User;

import java.util.function.BiFunction;
import java.util.random.RandomGenerator;

public class BankImpl implements Bank {

  private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

  @Override
  public BankCard createBankCard(User user, BankCardType cardType) {
    switch (cardType) {
      case DEBIT -> {
        return createBankCardByFunction(DebitBankCard::new, user);
      }
      case CREDIT -> {
        return createBankCardByFunction(CreditBankCard::new, user);
      }
      default -> throw new IllegalArgumentException("Wrong cardType");
    }
  }

  private BankCard createBankCardByFunction(BiFunction<String, User, BankCard> function,
      User user) {
    return function.apply(generateBankCardNumber(), user);
  }

  private String generateBankCardNumber() {
    var startCardNumber = "4528";
    return startCardNumber + randomGenerator.nextInt(1000, 9999);
  }
}
