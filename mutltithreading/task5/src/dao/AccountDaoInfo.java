package dao;

import exception.CurrencyNotFoundException;
import model.Account;
import model.Currency;
import util.AccountValidator;
import util.LogUtil;
import util.ResourceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class AccountDaoInfo {

  private static final Logger log = Logger.getLogger(AccountDaoInfo.class.getName());
  public static final String USERS_INFO_PROPERTIES = "users_info.properties";

  public Account find(String userName) {
    AccountValidator.verifyAccountExist(userName);

    String propsFileName = userName.toLowerCase() + ResourceUtil.PROPERTIES_POSTFIX;
    Properties accountProperties = ResourceUtil.loadProperties(propsFileName);
    return ResourceUtil.getAccountFromProperties(accountProperties);
  }

  public List<Account> findAll() {
    List<Account> allAccounts = new ArrayList<>();
    Properties accountProperties = ResourceUtil.loadProperties(USERS_INFO_PROPERTIES);

    Collection<Object> userNames = accountProperties.values();
    userNames.forEach(name -> {
      String propsFileName = name + ResourceUtil.PROPERTIES_POSTFIX;
      try (InputStream propsStream = ClassLoader.getSystemResourceAsStream(propsFileName)) {
        Properties singleAccount = new Properties();
        singleAccount.load(propsStream);
        allAccounts.add(ResourceUtil.getAccountFromProperties(singleAccount));
      } catch (IllegalArgumentException | IOException ex) {
        LogUtil.logRegularExceptions(AccountDaoInfo.class.getName(), ex);
      }
    });

    return allAccounts;
  }

  public void createAccount(String userName) {
    AccountValidator.verifyAccountExist(userName);
    ResourceUtil.createNewFile(userName);
  }

  public void updateAccount(Account account) {
    AccountValidator.verifyAccountExist(account.getUserId());
    ResourceUtil.updatePropertyFile(account);
  }

  public boolean removeAccount(String userName) {
    AccountValidator.verifyAccountExist(userName);
    String propsFileName = userName.toLowerCase() + ResourceUtil.PROPERTIES_POSTFIX;
    File fileToDelete = new File(ResourceUtil.PATH_PREFIX + propsFileName);
    return fileToDelete.delete();
  }

  public void depositCurrency(String userName, Currency currency, BigDecimal amount) {
    AccountValidator.verifyAccountExist(userName);
    String propsFileName = userName.toLowerCase() + ResourceUtil.PROPERTIES_POSTFIX;
    Properties accountProperties = ResourceUtil.loadProperties(propsFileName);

    setCurrencyValue(currency, amount, accountProperties);

    try {
      accountProperties.store(new FileOutputStream(ResourceUtil.PATH_PREFIX + propsFileName), null);
    } catch (IOException e) {
      LogUtil.logRegularExceptions(AccountDaoInfo.class.getName(), e);
    }

  }

  private void setCurrencyValue(Currency currency, BigDecimal amount,
      Properties accountProperties) {
    switch (currency) {
      case BYN:
        accountProperties.setProperty(ResourceUtil.BYN_CURRENCY_AMOUNT, String.valueOf(amount));
        break;
      case USD:
        accountProperties.setProperty(ResourceUtil.USD_CURRENCY_AMOUNT, String.valueOf(amount));
        break;
      default:
        throw new CurrencyNotFoundException(
            LogUtil.logBusinessException(AccountDaoInfo.class.getName(),
                "Currency with such name not found: " + currency));
    }
  }
}

