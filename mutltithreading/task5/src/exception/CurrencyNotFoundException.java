package exception;

public class CurrencyNotFoundException extends RuntimeException {

  public CurrencyNotFoundException(String s) {
    super(s);
  }
}
