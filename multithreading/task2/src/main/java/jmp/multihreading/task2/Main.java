package jmp.multihreading.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<Integer> nums = new ArrayList<>();
    Thread writer = new Thread(new Writer(nums));
    Thread sumPrinter = new Thread(new SumPrinter(nums));
    Thread pifagorPrinter = new Thread(new PifagorPrinter(nums));

    writer.start();
    sumPrinter.start();
    pifagorPrinter.start();
  }
}