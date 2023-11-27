package classic;

import java.util.Queue;
import java.util.random.RandomGenerator;

public class Producer implements Runnable {

  private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.getDefault();
  private final Queue<Integer> queue;
  private final int countOperations;


  public Producer(Queue<Integer> queue, int countOperations) {
    this.queue = queue;
    this.countOperations = countOperations;
  }

  @Override
  public void run() {
    for (int i = 0; i < countOperations; i++) {
      synchronized (queue) {
        queue.add(RANDOM_GENERATOR.nextInt(100));
      }
    }
  }
}
