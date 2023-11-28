package service;

import model.Account;
import model.Currency;

import java.math.BigDecimal;

public interface ExchangeService {

  BigDecimal convertCurrency(Currency from, Currency to, BigDecimal amount);

  void exchange(Account account, Currency from, Currency to, BigDecimal convertingAmount);
}
