package org.example.lab3.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer3Lock implements IBuffer {
    ReentrantLock consumerLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    ReentrantLock commonLock = new ReentrantLock();
    Condition condition = commonLock.newCondition();

    private int counter = 0;
    final int limit;
    public Buffer3Lock(int limit){
        this.limit = limit;
    }
    public void produce(int portion, int id) throws InterruptedException{
        producerLock.lock();
        commonLock.lock();
        while (counter + portion >= limit){
            condition.await();
        }
        counter+=portion;
        condition.signal();

        commonLock.unlock();
        producerLock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        consumerLock.lock();
        commonLock.lock();
        while (counter-portion <= 0){
            condition.await();
        }
        counter-=portion;
        condition.signal();
        commonLock.unlock();
        consumerLock.unlock();
    }

    public int getLimit() {
        return limit;
    }
}
// Consumer id: 1, portion: 1, times consumed: 99723
// ...
// Consumer id: 2, portion: 4, times consumed: 46332
// Consumer id: 2, portion: 4, times consumed: 46333
// Producent id: 1, portion: 1, times produced: 101593