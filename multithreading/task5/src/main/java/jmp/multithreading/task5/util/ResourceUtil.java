package jmp.multithreading.task5.util;

import jmp.multithreading.task5.model.Account;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

public class ResourceUtil {

  private static final Logger log = Logger.getLogger(ResourceUtil.class.getName());

  public static final String USER_ID = "user.id";
  public static final String BYN_CURRENCY_AMOUNT = "byn.currency.amount";
  public static final String USD_CURRENCY_AMOUNT = "usd.currency.amount";
  public static final String PATH_PREFIX = "src/main/resources/";
  public static final String PROPERTIES_POSTFIX = "_acc.properties";

  public static Account getAccountFromProperties(Properties accountProperties) {
    String userId = accountProperties.getProperty(USER_ID);
    BigDecimal bynCurrencyAmount = BigDecimal.valueOf(
        Double.parseDouble(accountProperties.getProperty(BYN_CURRENCY_AMOUNT)));
    BigDecimal usdCurrencyAmount = BigDecimal.valueOf(
        Double.parseDouble(accountProperties.getProperty(USD_CURRENCY_AMOUNT)));

    return new Account(userId, bynCurrencyAmount, usdCurrencyAmount);
  }

  public static Properties loadProperties(String propsFileName) {
    Properties accountProperties = new Properties();

    try (InputStream propsStream = ClassLoader.getSystemResourceAsStream(propsFileName)) {
      accountProperties.load(propsStream);
    } catch (IllegalArgumentException | IOException e) {
      LogUtil.logRegularExceptions(ResourceUtil.class.getName(), e);
    }
    return accountProperties;
  }

  public static void updatePropertyFile(Account account) {
    String propsFileName = account.getUserId().toLowerCase() + PROPERTIES_POSTFIX;
    Properties accountProperties = ResourceUtil.loadProperties(propsFileName);

    accountProperties.setProperty(BYN_CURRENCY_AMOUNT,
        String.valueOf(account.getBynCurrency()));
    accountProperties.setProperty(USD_CURRENCY_AMOUNT,
        String.valueOf(account.getUsdCurrency()));
    try {
      accountProperties.store(Files.newOutputStream(Paths.get(PATH_PREFIX + propsFileName)), null);
    } catch (IOException e) {
      LogUtil.logRegularExceptions(ResourceUtil.class.getName(), e);
    }
  }

  public static void createNewFile(String userName) {
    String propsFileName = userName.toLowerCase() + PROPERTIES_POSTFIX;
    Properties accountProperties = new Properties();
    accountProperties.setProperty(USER_ID, userName);
    accountProperties.setProperty(BYN_CURRENCY_AMOUNT, String.valueOf(0));
    accountProperties.setProperty(USD_CURRENCY_AMOUNT, String.valueOf(0));

    try {
      accountProperties.store(Files.newOutputStream(Paths.get(PATH_PREFIX + propsFileName)), null);
    } catch (IOException e) {
      LogUtil.logRegularExceptions(ResourceUtil.class.getName(), e);
    }
  }

}
