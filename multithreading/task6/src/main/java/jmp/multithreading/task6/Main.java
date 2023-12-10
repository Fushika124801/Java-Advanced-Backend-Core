package jmp.multithreading.task6;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import static java.lang.String.format;

public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) throws InterruptedException {
    int countOperations = 10000;
    Queue<Integer> queue = new LinkedList<>();
    jmp.multithreading.task6.classic.Consumer consumer = new jmp.multithreading.task6.classic.Consumer(queue, countOperations);
    Thread threadClassicConsumer = new Thread(consumer);
    jmp.multithreading.task6.classic.Producer producer = new jmp.multithreading.task6.classic.Producer(queue, countOperations);
    Thread threadClassicProducer = new Thread(producer);

    double starTime = System.currentTimeMillis() * 1000.0;
    threadClassicProducer.start();
    threadClassicConsumer.start();
    threadClassicConsumer.join();
    threadClassicProducer.join();
    double endTime = System.currentTimeMillis() * 1000.0;
    double time = countOperations * 2 / (endTime - starTime);

    BlockingQueue<Integer> concurrentQueue = new LinkedBlockingQueue<>();
    jmp.multithreading.task6.concurrency.Consumer concurrencyConsumer = new jmp.multithreading.task6.concurrency.Consumer(concurrentQueue,
        countOperations);
    Thread threadConcurrencyConsumer = new Thread(concurrencyConsumer);
    jmp.multithreading.task6.concurrency.Producer concurrencyProducer = new jmp.multithreading.task6.concurrency.Producer(concurrentQueue,
        countOperations);
    Thread threadConcurrencyProducer = new Thread(concurrencyProducer);

    double concurrencyStarTime = System.currentTimeMillis() * 1000.0;
    threadConcurrencyProducer.start();
    threadConcurrencyConsumer.start();
    threadConcurrencyProducer.join();
    threadConcurrencyConsumer.join();
    double concurrencyEndTime = System.currentTimeMillis() * 1000.0;
    double concurrencyTime = countOperations * 2 / (concurrencyEndTime - concurrencyStarTime);

    LOGGER.info(format("Classic: %s ops/sec\n", time));
    LOGGER.info(format("Concurrency: %s ops/sec", concurrencyTime));
  }
}
