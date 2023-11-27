package service.impl;

import dao.ExchangeDaoRate;
import exception.CurrencyNotFoundException;
import exception.IllegalCurrencyException;
import model.Account;
import model.Currency;
import model.ExchangeRate;
import service.ExchangeService;
import util.BalanceValidator;
import util.LogUtil;
import util.ResourceUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

public class ExchangeServiceImpl implements ExchangeService {

  private static final Logger LOGGER = Logger.getLogger(ExchangeServiceImpl.class.getName());
  ExchangeDaoRate exchangeDaoRate = new ExchangeDaoRate();


  @Override
  public void exchange(Account account, Currency from, Currency to, BigDecimal amount) {
    final BigDecimal zero = BigDecimal.valueOf(0);
    BigDecimal newValueTo;
    // when we work with money we should be too careful
    // seems like there isn't enough transaction(synchronize) section
    // from row 35 to row 37 - amount can be changed
    BalanceValidator.checkBalanceNotZero(account, zero);

    setAppropriatedCurrencyFrom(account, from, amount);
    newValueTo = convertCurrency(from, to, amount);
    setAppropriatedCurrencyTo(account, to, newValueTo);

    ResourceUtil.updatePropertyFile(account);

    LogUtil.logInfo(ExchangeServiceImpl.class.getName(),
        String.format("Exchange between [%s] and [%s] done successfully", from.name(), to.name()));

    LogUtil.logInfo(ExchangeServiceImpl.class.getName(),
        "Thread with name " + Thread.currentThread().getName() + " finished job for user: "
            + account.getUserId());
  }

  @Override
  public BigDecimal convertCurrency(Currency from, Currency to, BigDecimal amount) {
    ExchangeRate exchangeRate = exchangeDaoRate.get();
//why not? ) if convert from to with the same value we will get the same amount, but up to you
    if (from == to) {
      throw new IllegalCurrencyException(
          LogUtil.logBusinessException(ExchangeServiceImpl.class.getName(),
              String.format("Cannot convert equal type of currency: [%s, %s]", from,
                  to)));
    }

    LogUtil.logInfo(ExchangeServiceImpl.class.getName(),
        String.format("Converting from [%s] to [%s] currency...", from.name(),
            to.name()));

    return getConvertedValue(from, amount, exchangeRate);
  }

  // "Currency to" not used
  // there are three currency in enum bu here only two
  private BigDecimal getConvertedValue(Currency from,
      BigDecimal amount, ExchangeRate exchangeRate) {
    switch (from) {
      case USD:
        return amount.multiply(exchangeRate.getBynExchangeRate());
      case BYN:
        return amount.divide(exchangeRate.getUsdExchangeRate(), RoundingMode.DOWN);
      default:
        throw new CurrencyNotFoundException(
            LogUtil.logBusinessException(ExchangeServiceImpl.class.getName(),
                "Currency with such name not found: " + from));
    }
  }

  private void setAppropriatedCurrencyTo(Account account, Currency to,
      BigDecimal newValueTo) {
    switch (to) {
      case USD:
        account.setUsdCurrency(account.getUsdCurrency().add(newValueTo));
        break;
      case BYN:
        account.setBynCurrency(account.getBynCurrency().add(newValueTo));
        break;
      default:
        throw new CurrencyNotFoundException(
            LogUtil.logBusinessException(ExchangeServiceImpl.class.getName(),
                "Currency with such name not found: " + to));
    }
  }

  private void setAppropriatedCurrencyFrom(Account account, Currency from,
      BigDecimal convertingAmount) {
    switch (from) {
      case USD:
        BigDecimal amountUsd = account.getUsdCurrency();
        BalanceValidator.checkAmountOnAccount(amountUsd, convertingAmount);
        account.setUsdCurrency(amountUsd.subtract(convertingAmount));
        break;
      case BYN:
        BigDecimal amountByn = account.getBynCurrency();
        BalanceValidator.checkAmountOnAccount(amountByn, convertingAmount);
        account.setBynCurrency(amountByn.subtract(convertingAmount));
        break;
      default:
        throw new CurrencyNotFoundException(
            LogUtil.logBusinessException(ExchangeServiceImpl.class.getName(),
                "Currency with such name not found: " + from));
    }

  }
}
