package util;

import exception.IllegalCurrencyException;
import model.Account;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class BalanceValidator {

  private static final Logger LOGGER = Logger.getLogger(BalanceValidator.class.getName());

  public static void checkBalanceNotZero(Account account, BigDecimal zero) {
    if (account.getBynCurrency().compareTo(zero) == 0
        && account.getUsdCurrency().compareTo(zero) == 0) {
      throw new IllegalCurrencyException(
          LogUtil.logBusinessException(BalanceValidator.class.getName(),
              "Not enough money to proceed exchange"));
    }
  }

  public static void checkAmountOnAccount(BigDecimal amountOnAcc, BigDecimal newValueFrom) {
    if (amountOnAcc.compareTo(newValueFrom) < 0) {
      throw new IllegalCurrencyException(
          LogUtil.logBusinessException(BalanceValidator.class.getName(),
              String.format("Not enough money to proceed exchange. " +
                  "Tried to convert: [%s], but have on balance: [%s]", newValueFrom, amountOnAcc)));
    }
  }
}
