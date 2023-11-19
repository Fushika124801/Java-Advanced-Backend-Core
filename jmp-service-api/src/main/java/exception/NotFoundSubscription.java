package exception;

public class NotFoundSubscription extends RuntimeException {

  public NotFoundSubscription(String message) {
    super(message);
  }
}
