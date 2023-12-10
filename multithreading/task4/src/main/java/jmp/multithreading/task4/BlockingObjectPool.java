package jmp.multithreading.task4;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingObjectPool {

  private final ArrayBlockingQueue<Object> pool;

  /**
   * Creates filled pool of passed size
   *
   * @param size of pool
   */
  public BlockingObjectPool(int size) {
    this.pool = new ArrayBlockingQueue<>(size);
  }

  /**
   * Gets object from pool or blocks if pool is empty
   *
   * @return object from pool
   */
  public Object get() throws InterruptedException {
    return pool.take();
  }

  /**
   * Puts object to pool or blocks if pool is full
   *
   * @param object to be taken back to pool
   */
  public void take(Object object) {
    if (this.pool.offer(object)) {
      System.out.printf("Object successfully added to pool:%s%n", object);
    }
  }


}

