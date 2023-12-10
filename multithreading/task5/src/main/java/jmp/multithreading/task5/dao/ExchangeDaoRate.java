package jmp.multithreading.task5.dao;

import jmp.multithreading.task5.model.ExchangeRate;
import jmp.multithreading.task5.util.ResourceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeDaoRate {

  private static final Logger log = Logger.getLogger(ExchangeDaoRate.class.getName());
  public static final String EXCHANGE_RATES_PROPERTIES = "exchange_rates.properties";
  public static final String USD_KZT_RATE = "usd.rate";
  public static final String BYN_KZT_RATE = "byn.rate";


  public ExchangeRate get() {
    Properties exchangeProperty = ResourceUtil.loadProperties(EXCHANGE_RATES_PROPERTIES);
    BigDecimal usdKztExchangeRate = BigDecimal.valueOf(
        Double.parseDouble(exchangeProperty.getProperty(USD_KZT_RATE)));
    BigDecimal plnKztExchangeRate = BigDecimal.valueOf(
        Double.parseDouble(exchangeProperty.getProperty(BYN_KZT_RATE)));

    return new ExchangeRate(usdKztExchangeRate, plnKztExchangeRate);
  }

  public void updateRates(ExchangeRate rate) {
    Properties exchangeProperty = ResourceUtil.loadProperties(EXCHANGE_RATES_PROPERTIES);
    exchangeProperty.setProperty(USD_KZT_RATE, String.valueOf(rate.getUsdExchangeRate()));
    exchangeProperty.setProperty(BYN_KZT_RATE, String.valueOf(rate.getBynExchangeRate()));

    try {
      exchangeProperty.store(
          new FileOutputStream(ResourceUtil.PATH_PREFIX + EXCHANGE_RATES_PROPERTIES), null);
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public void createRates(ExchangeRate rate) {
    Properties rateProperties = new Properties();
    rateProperties.setProperty(USD_KZT_RATE, String.valueOf(rate.getUsdExchangeRate()));
    rateProperties.setProperty(BYN_KZT_RATE, String.valueOf(rate.getBynExchangeRate()));

    try {
      rateProperties.store(
          new FileOutputStream(ResourceUtil.PATH_PREFIX + EXCHANGE_RATES_PROPERTIES), null);
    } catch (IOException e) {
      log.log(Level.SEVERE, e.getMessage());
    }
  }

  public boolean removeRates() {
    File fileToDelete = new File(ResourceUtil.PATH_PREFIX + EXCHANGE_RATES_PROPERTIES);
    return fileToDelete.delete();
  }
}

