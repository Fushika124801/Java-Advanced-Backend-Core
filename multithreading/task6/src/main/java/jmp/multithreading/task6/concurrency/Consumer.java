package jmp.multithreading.task6.concurrency;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

  private final BlockingQueue<Integer> queue;
  private final int countOperations;

  public Consumer(BlockingQueue<Integer> queue, int countOperations) {
    this.queue = queue;
    this.countOperations = countOperations;
  }

  @Override
  public void run() {
    for (int i = 0; i < countOperations; i++) {
      long sum = queue.stream().mapToInt(Integer::intValue).sum();
      System.out.println(sum);
    }
  }
}
