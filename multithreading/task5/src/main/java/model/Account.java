package model;

import java.math.BigDecimal;

public class Account {

  private String userId;
  private BigDecimal bynCurrency;
  private BigDecimal usdCurrency;


  public Account(String userId, BigDecimal bynCurrency, BigDecimal usdCurrency) {
    this.userId = userId;
    this.bynCurrency = bynCurrency;
    this.usdCurrency = usdCurrency;
  }

  public BigDecimal getBynCurrency() {
    return bynCurrency;
  }

  public void setBynCurrency(BigDecimal bynCurrency) {
    this.bynCurrency = bynCurrency;
  }

  public BigDecimal getUsdCurrency() {
    return usdCurrency;
  }

  public void setUsdCurrency(BigDecimal usdCurrency) {
    this.usdCurrency = usdCurrency;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
