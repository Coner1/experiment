package org.waka.experiment.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by coner on 16-9-21.
 */
public class ConcurrentLock {

    public static void main(String[] args) {
        Account account = new Account();

        ExecutorService executor = Executors.newFixedThreadPool(8);
        executor.submit(new AddTask(account));
        executor.submit(new ReduceTask(account));
        executor.submit(new AddTask(account));
        executor.submit(new ReduceTask(account));
        executor.submit(new AddTask(account));
        executor.submit(new ReduceTask(account));
        executor.submit(new AddTask(account));
        executor.submit(new ReduceTask(account));
        executor.shutdown();


    }




    private static class Account{
        private Lock lock = new ReentrantLock();

        private Condition condition = lock.newCondition();
        private Condition condition1 = lock.newCondition();


        private long money = 0;

        void addMoney(long m){

            lock.lock();
            try {
                money = money + m;
                System.out.println("+,"+showMoney());
            }finally {
                lock.unlock();
            }

        }

        void ruduceMoney(long m){
            lock.lock();
            try {
                money = money - m;
                System.out.println("-,"+showMoney());
            }finally {
                lock.unlock();
            }
        }

        long showMoney(){
            return money;
        }

    }


    private static class AddTask implements Runnable{

        private Account account;

        public AddTask(Account account){
            this.account = account;
        }

        public void run() {
            int count = 0 ;
            while (count < 100000){
                this.account.addMoney(1);
                count++;
//                try {
//                    Thread.sleep(new Double(10*Math.random()).intValue());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }



        }
    }
    private static class ReduceTask implements Runnable{

        private Account account;

        public ReduceTask(Account account){
            this.account = account;
        }

        public void run() {
            int count = 0 ;
            while (count < 100000){
                this.account.ruduceMoney(1);
                count++;
//                try {
//                    Thread.sleep(new Double(10*Math.random()).intValue());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }



        }
    }


}


