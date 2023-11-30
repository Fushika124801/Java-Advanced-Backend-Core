package jmp.multithreading.task5.service;

import jmp.multithreading.task5.model.Account;
import jmp.multithreading.task5.model.Currency;

import java.math.BigDecimal;

public interface ExchangeService {

  BigDecimal convertCurrency(Currency from, Currency to, BigDecimal amount);

  void exchange(Account account, Currency from, Currency to, BigDecimal convertingAmount);
}
