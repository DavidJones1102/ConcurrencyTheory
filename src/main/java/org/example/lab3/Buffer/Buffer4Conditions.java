package org.example.lab3.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer4Conditions implements IBuffer{
    ReentrantLock lock = new ReentrantLock();
    Condition firstConsumer = lock.newCondition();
    Condition restConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition restProducers = lock.newCondition();
    private int counter = 0;
    final int limit;
    public Buffer4Conditions(int limit){
        this.limit = limit;
    }
    public void produce(int portion, int id) throws InterruptedException{
        lock.lock();
        int tries = 0;
        while (lock.hasWaiters(firstProducer)) {
            restProducers.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to produce "+portion);
        }
        while (counter + portion >= limit){
            firstProducer.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to produce "+portion);
        }

        counter+=portion;
        System.out.println(id + " Produce "+portion);
        restProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        int tries = 0;
        while (lock.hasWaiters(firstConsumer)) {
            restConsumers.await();
            tries++;
            System.out.println(id + "I am waiting "+tries+" time, wanted to consume "+portion);
        }
        while (counter-portion <= 0){
            firstConsumer.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to consume "+portion);
        }
        counter-=portion;
        System.out.println(id + " Consume "+portion);

        restConsumers.signal();
        firstProducer.signal();
        lock.unlock();
    }

    public int getLimit() {
        return limit;
    }
}
