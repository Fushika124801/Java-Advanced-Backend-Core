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
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum:" + sum);
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

    }
  }
}
