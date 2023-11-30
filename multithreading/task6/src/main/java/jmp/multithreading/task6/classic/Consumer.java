package jmp.multithreading.task6.classic;

import java.util.Queue;

public class Consumer implements Runnable {

  private final Queue<Integer> queue;
  private final int countOperations;

  public Consumer(Queue<Integer> queue, int countOperations) {
    this.queue = queue;
    this.countOperations = countOperations;
  }

  @Override
  public void run() {
    for (int i = 0; i < countOperations; i++) {
      synchronized (queue) {
        long sum = queue.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
      }
    }
  }
}
