package jmp.multihreading.task1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentThreadSafeMap {

  private final Map<Integer, Integer> map = new ConcurrentHashMap<>();
  private int sum;
  private final int numberOfValues;

  public ConcurrentThreadSafeMap(int numberOfValues) {
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
      map.put(i, i);
    }
  }

  private void sumValuesOfMap() {
    for (int value : map.values()) {
      sum += value;
    }
  }

  public int getSum() {
    return sum;
  }

}
