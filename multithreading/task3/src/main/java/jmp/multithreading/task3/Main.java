package jmp.multithreading.task3;

public class Main {

  public static void main(String[] args) {
    BusQueue busQueue = new BusQueue(5);
    Producer producer = new Producer(busQueue);
    Consumer consumer = new Consumer(busQueue);

    Thread consumerThread = new Thread(consumer);
    Thread producerThread = new Thread(producer);
    producerThread.start();
    consumerThread.start();
  }
}
