package jmp.multihreading.task2;

import java.util.List;

public class SumPrinter implements Runnable {

  private final List<Integer> nums;

  public SumPrinter(List<Integer> nums) {
    this.nums = nums;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (nums) {
        nums.notify();
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum:" + sum);
        try {
          nums.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
