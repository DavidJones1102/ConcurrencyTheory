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
        while (firstProducerWaiting) {
            restProducers.await();
        }
        while (counter + portion >= limit){
            firstProducerWaiting = true;
            firstProducer.await();
        }

        firstProducerWaiting = false;
        counter+=portion;
        restProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        while (firstConsumerWaiting) {
            restConsumers.await();
        }
        while (counter-portion <= 0){
            firstConsumerWaiting = true;
            firstConsumer.await();
        }

        firstConsumerWaiting = false;
        counter-=portion;

        restConsumers.signal();
        firstProducer.signal();
        lock.unlock();
    }

    public int getLimit() {
        return limit;
    }
}
// Producent id: 1, portion: 1, times produced: 71877
// Producent id: 2, portion: 4, times produced: 99048

// Consumer id: 2, portion: 4, times consumed: 99628
// Consumer id: 1, portion: 1, times consumed: 69564