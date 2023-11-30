package jmp.multihreading.task2;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Writer implements Runnable {

  private final List<Integer> nums;

  public Writer(List<Integer> nums) {
    this.nums = nums;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (nums) {
        nums.notify();
        nums.add(ThreadLocalRandom.current().nextInt(10));
        try {
          nums.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
