import java.util.HashMap;
import java.util.Map;

public class SynchronizedThreadSafeMap {

  private final Map<Integer, Integer> map = new HashMap<>();
  private int sum;
  private final int numberOfValues;

  public SynchronizedThreadSafeMap(int numberOfValues) {
    this.numberOfValues = numberOfValues;
  }

  public void addAndSumValues() throws InterruptedException {
    Thread adding = new Thread(this::addElementsIntoMap);
    Thread summing = new Thread(this::sumValuesOfMap);
    adding.start();
    summing.start();

    adding.join();
    summing.join();
  }


  private void addElementsIntoMap() {
    for (int i = 0; i <= numberOfValues; i++) {
      synchronized (map) {
        map.put(i, i);
      }
    }
  }

  private void sumValuesOfMap() {
    synchronized (map) {
      for (Integer value : map.values()) {
        sum += value;
      }
    }
  }

  public int getSum() {
    return sum;
  }

}
