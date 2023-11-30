package jmp.multithreading.task6.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.random.RandomGenerator;

public class Producer implements Runnable {

  private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.getDefault();
  private final BlockingQueue<Integer> queue;
  private final int countOperations;


  public Producer(BlockingQueue<Integer> queue, int countOperations) {
    this.queue = queue;
    this.countOperations = countOperations;
  }

  @Override
  public void run() {
    for (int i = 0; i < countOperations; i++) {
      queue.add(RANDOM_GENERATOR.nextInt(100));
    }
  }
}
