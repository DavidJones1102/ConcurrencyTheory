package org.example.lab3.Buffer;

import org.example.lab3.StopWatch;

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
    private int operations = 0;
    private final StopWatch watch = new StopWatch();
    final int limit;
    public Buffer4Conditions2Bool(int limit){
        this.limit = limit;
    }
    public void produce(int portion, int id) throws InterruptedException{
        lock.lock();
        watch.start();
        while (firstProducerWaiting) {
            watch.stop();
            restProducers.await();
            watch.start();
        }
        while (counter + portion >= limit){
            firstProducerWaiting = true;
            watch.stop();
            firstProducer.await();
            watch.start();
        }

        firstProducerWaiting = false;
        counter+=portion;
        restProducers.signal();
        firstConsumer.signal();
        operations++;
        watch.stop();
        if(operations>=100000){
            System.out.println(watch.getTime());
            System.exit(0);
        }
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        watch.start();
        while (firstConsumerWaiting) {
            watch.stop();
            restConsumers.await();
            watch.start();
        }
        while (counter-portion <= 0){
            firstConsumerWaiting = true;
            watch.stop();
            firstConsumer.await();
            watch.start();
        }

        firstConsumerWaiting = false;
        counter-=portion;

        restConsumers.signal();
        firstProducer.signal();
        operations++;
        watch.stop();
        if(operations>=100000){
            System.out.println(watch.getTime());
            System.exit(0);
        }
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