package bank;

import dto.BankCard;
import dto.BankCardType;
import dto.User;

public interface Bank {

  BankCard createBankCard(User user, BankCardType cardType);
}
