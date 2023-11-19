package dto;

public class BankCard {

  private String number;
  private User user;

  public BankCard(String number, User user) {
    this.number = number;
    this.user = user;
  }

  public BankCard() {
  }

  public String getNumber() {
    return number;
  }

  public User getUser() {
    return user;
  }
}
