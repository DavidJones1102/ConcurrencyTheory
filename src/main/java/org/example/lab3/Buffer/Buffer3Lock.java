package org.example.lab3.Buffer;

import org.example.lab3.StopWatch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer3Lock implements IBuffer {
    ReentrantLock consumerLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    ReentrantLock commonLock = new ReentrantLock();
    Condition condition = commonLock.newCondition();

    private final StopWatch watch = new StopWatch();
    private int counter = 0;
    private int operations = 0;
    final int limit;
    public Buffer3Lock(int limit){
        this.limit = limit;
    }
    public void produce(int portion, int id) throws InterruptedException{
        producerLock.lock();
        commonLock.lock();
        watch.start();
        while (counter + portion >= limit){
            watch.stop();
            condition.await();
            watch.start();
        }
        counter+=portion;
        condition.signal();
        operations++;
        watch.stop();
        if(operations>=100000){
            System.out.println(watch.getTime());
            System.exit(0);
        }
        commonLock.unlock();
        producerLock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        consumerLock.lock();
        commonLock.lock();
        watch.start();
        while (counter-portion <= 0){
            watch.stop();
            condition.await();
            watch.start();
        }
        counter-=portion;
        condition.signal();
        operations++;
        watch.stop();
        if(operations>=100000){
            System.out.println(watch.getTime());
            System.exit(0);
        }
        commonLock.unlock();
        consumerLock.unlock();
    }

    public int getLimit() {
        return limit;
    }
    public long getTime(){
        return watch.getTime();
    }
}
// czy to są 3 monitory?
