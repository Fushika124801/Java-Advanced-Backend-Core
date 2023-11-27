public class Main {

  public static void main(String[] args) throws InterruptedException {
    double startTime = System.currentTimeMillis();

    SynchronizedThreadSafeMap threadSafeMap = new SynchronizedThreadSafeMap(100000);
    threadSafeMap.addAndSumValues();

    threadSafeMap.getSum();

    double endTime = System.currentTimeMillis();

    System.out.println("Java version: " + System.getProperty("java.version"));
    System.out.println("Execution time:" + (endTime - startTime));

  }

}