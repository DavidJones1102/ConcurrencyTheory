package org.example.lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Status {
    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    private int counter = 0;
    final int limit = 10;
    public void setToReady() {
        lock.lock();
        while (counter >= limit){
            try {
                notFull.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter++;
        System.out.println("Produce "+counter);
        notEmpty.signal();
        lock.unlock();
    }
    public void setToTaken() {
        lock.lock();
        while (counter <= 0){
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter--;
        System.out.println("Consume "+counter);
        notFull.signal();
        lock.unlock();
    }
}
