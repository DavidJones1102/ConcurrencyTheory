package org.example.lab3.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer4Conditions2Bool implements IBuffer{
    ReentrantLock lock = new ReentrantLock();
    Condition firstConsumer = lock.newCondition();
    private boolean firstConsumerWaiting = false;
    Condition restConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    private boolean firstProducerWaiting = false;
    Condition restProducers = lock.newCondition();
    private int counter = 0;
    final int limit;
    public Buffer4Conditions2Bool(int limit){
        this.limit = limit;
    }
    public void produce(int portion, int id) throws InterruptedException{
        lock.lock();
        int tries = 0;
        while (firstProducerWaiting) {
            restProducers.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to produce "+portion);
        }
        while (counter + portion >= limit){
            firstProducerWaiting = true;
            firstProducer.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to produce "+portion);
        }

        firstProducerWaiting = false;
        counter+=portion;
        System.out.println(id + " Produce "+portion);
        restProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        int tries = 0;
        while (firstConsumerWaiting) {
            restConsumers.await();
            tries++;
            System.out.println(id + "I am waiting "+tries+" time, wanted to consume "+portion);
        }
        while (counter-portion <= 0){
            firstConsumerWaiting = true;
            firstConsumer.await();
            tries++;
            System.out.println(id + " I am waiting "+tries+" time, wanted to consume "+portion);
        }

        firstConsumerWaiting = false;
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
