package org.waka.experiment.concurrent;

/**
 * Created by coner on 16-9-19.
 */
public class NoVisibility {
    private static volatile boolean ready;

    private static double number = 134234234324L;


    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();

        Thread.dumpStack();
        Thread.currentThread().getStackTrace();
        new WriteThread().start();

    }

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            number ++;
        }
    }
    private static class WriteThread extends Thread{
        @Override
        public void run() {
            System.out.println(number);
        }
    }
}
