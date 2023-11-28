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
        nums.add(ThreadLocalRandom.current().nextInt(10));
      }
    }
  }
}
