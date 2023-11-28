import java.util.List;

public class PifagorPrinter implements Runnable {

  private final List<Integer> nums;

  public PifagorPrinter(List<Integer> nums) {
    this.nums = nums;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (nums) {
        int sum = nums.stream().mapToInt(num -> (int) Math.pow(num, 2)).sum();
        System.out.println("Sqrt sum:" + Math.sqrt(sum));
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

    }
  }
}
