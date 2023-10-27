package org.example.lab3.Buffer;

import org.example.lab3.Buffer.IBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferStarve implements IBuffer {
    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    private int counter = 0;
    final int limit;
    public BufferStarve(int limit){
        this.limit = limit;
    }
    public void produce(int portion) {
        lock.lock();
        int tries = 0;
        while (counter + portion >= limit){
            try {
                notFull.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tries++;
            System.out.println("I am waiting "+tries+" time, wanted to produce "+portion);
        }
        counter+=portion;
        System.out.println("Produce "+counter);
        notEmpty.signal();
        lock.unlock();
    }
    public void consume(int portion) {
        lock.lock();
        int tries = 0;
        while (counter-portion <= 0){
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tries++;
            System.out.println("I am waiting "+tries+" time, wanted to consume "+portion);
        }
        counter-=portion;
        System.out.println("Consume "+counter);
        notFull.signal();
        lock.unlock();
    }

    public int getLimit() {
        return limit;
    }
}
