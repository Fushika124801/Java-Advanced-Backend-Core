package jmp.multithreading.task4;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    BlockingObjectPool pool = new BlockingObjectPool(3);

    pool.take(1);
    pool.take(2);
    pool.take(3);

    pool.get();
  }
}