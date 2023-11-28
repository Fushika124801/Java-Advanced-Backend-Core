import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

  private static final String TOPIC = "test1";
  private static final String MESSAGE_TEXT = "hi";
  private final BusQueue queue;
  private volatile boolean runFlag;

  public Producer(BusQueue queue) {
    this.queue = queue;
    runFlag = true;
  }

  @Override
  public void run() {
    try {
      produce();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void produce() throws InterruptedException {
    while (runFlag) {
      Message message = generateMessage();
      while (queue.isFull()) {
        try {
          queue.waitOnFull();
        } catch (InterruptedException e) {
          e.printStackTrace();
          break;
        }
      }
      if (!runFlag) {
        break;
      }
      queue.add(message);
      queue.notifyAllForEmpty();
    }
    System.out.println("Producer Stopped");
  }

  private Message generateMessage() throws InterruptedException {
    Message message = new Message(TOPIC, MESSAGE_TEXT + ThreadLocalRandom.current().nextInt(3, 10));
    System.out.printf("Thread: %s, Topic: %s, Text: %s\n",
        Thread.currentThread().getName(), message.getTopic(), message.getText());

    Thread.sleep(1000);

    return message;
  }

  public void stop() {
    runFlag = false;
    queue.notifyAllForFull();
  }
}
