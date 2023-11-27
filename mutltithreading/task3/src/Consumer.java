public class Consumer implements Runnable {

  private final BusQueue queue;
  private volatile boolean runFlag;

  public Consumer(BusQueue queue) {
    this.queue = queue;
    runFlag = true;
  }

  @Override
  public void run() {
    try {
      consume();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void consume() throws InterruptedException {
    while (runFlag) {
      Message message;
      if (queue.isEmpty()) {
        try {
          queue.waitOnEmpty();
        } catch (InterruptedException e) {
          e.printStackTrace();
          break;
        }
      }
      if (!runFlag) {
        break;
      }
      message = queue.remove();
      queue.notifyAllForFull();
      useMessage(message);
    }
    System.out.println("Consumer Stopped");
  }

  private void useMessage(Message message) throws InterruptedException {
    if (message != null) {
      System.out.printf("Thread: %s, Topic: %s, Text: %s\n",
          Thread.currentThread().getName(), message.getTopic(), message.getText());

    }
    Thread.sleep(1000);
  }

  public void stop() {
    runFlag = false;
    queue.notifyAllForEmpty();
  }
}

