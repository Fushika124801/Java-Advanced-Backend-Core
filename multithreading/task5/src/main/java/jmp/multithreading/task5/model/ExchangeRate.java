package jmp.multithreading.task5.model;

import java.math.BigDecimal;

public class ExchangeRate {

  private BigDecimal bynExchangeRate;
  private BigDecimal usdExchangeRate;


  public ExchangeRate(BigDecimal bynExchangeRate, BigDecimal usdExchangeRate) {
    this.bynExchangeRate = bynExchangeRate;
    this.usdExchangeRate = usdExchangeRate;
  }

  public BigDecimal getBynExchangeRate() {
    return bynExchangeRate;
  }

  public void setBynExchangeRate(BigDecimal bynExchangeRate) {
    this.bynExchangeRate = bynExchangeRate;
  }

  public BigDecimal getUsdExchangeRate() {
    return usdExchangeRate;
  }

  public void setUsdExchangeRate(BigDecimal usdExchangeRate) {
    this.usdExchangeRate = usdExchangeRate;
  }
}
