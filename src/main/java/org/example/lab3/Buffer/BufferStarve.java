package org.example.lab3.Buffer;


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
    public void produce(int portion, int id) throws InterruptedException{
        lock.lock();
        while (counter + portion >= limit){
            notFull.await();
        }
        counter+=portion;
        notEmpty.signal();
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        while (counter-portion <= 0){
            notEmpty.await();
        }
        counter-=portion;
        notFull.signal();
        lock.unlock();
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