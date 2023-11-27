package util;

import dao.AccountDaoInfo;
import exception.AccountNotFoundException;

public class AccountValidator {

  private static final AccountDaoInfo accountInfoDAO = new AccountDaoInfo();

  public static void verifyAccountExist(String userId) {
    final boolean accExist = accountInfoDAO.findAll().stream()
        .anyMatch(account -> account.getUserId().equals(userId));
    if (!accExist) {
      throw new AccountNotFoundException(
          LogUtil.logBusinessException(AccountValidator.class.getName(),
              String.format("Account with id [%s] not found", userId)));
    }
  }
}

