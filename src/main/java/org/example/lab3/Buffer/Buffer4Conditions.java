package org.example.lab3.Buffer;

import org.example.lab3.StopWatch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer4Conditions implements IBuffer{
    ReentrantLock lock = new ReentrantLock();
    Condition firstConsumer = lock.newCondition();
    Condition restConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition restProducers = lock.newCondition();
    StopWatch watch = new StopWatch();
    private int counter = 0;
    private int operations;
    final int limit;
    private int stop;
    public Buffer4Conditions(int limit, int stop){
        this.limit = limit;
        this.stop = stop;
    }
    public void produce(int portion, int id) throws InterruptedException{
        lock.lock();
        watch.start();
        while (lock.hasWaiters(firstProducer)) {
            watch.stop();
            restProducers.await();  // p(5), p(6), p(3)
            watch.start();
        }
        while (counter + portion >= limit){
            watch.stop();
            firstProducer.await();
            watch.start();// p(2)
        }

        counter+=portion;
        restProducers.signal();
        firstConsumer.signal();
        operations++;
        watch.stop();
        if(operations>=stop){
            System.out.println(watch.getTime());
            System.exit(0);
        }
        lock.unlock();
    }
    public void consume(int portion, int id) throws InterruptedException{
        lock.lock();
        watch.start();
        while (lock.hasWaiters(firstConsumer)) {
            watch.stop();
            restConsumers.await(); // c(7)
            watch.start();
        }
        while (counter-portion <= 0){
            watch.stop();
            // print
            firstConsumer.await();  // c(10)
            watch.start();
        }
        counter-=portion;

        // done
        restConsumers.signal();
        firstProducer.signal();
        operations++;
        watch.stop();
        if(operations>=stop){
            System.out.println(watch.getTime());
            System.exit(0);
        }
        lock.unlock();
    }

    public int getLimit() {
        return limit;
    }
}
//

// Producer id: 2, portion: 4, times produced: 53109
// ...
// Consumer id: 2, portion: 4, times consumed: 52849
// Consumer id: 1, portion: 1, times consumed: 74996
// Consumer id: 1, portion: 1, times consumed: 74997
// Producer id: 1, portion: 1, times produced: 73958

// deadlock hasWaiters()
// firstProducer: id1, restProducer: 2,3,4
// consumer signal first producer
// consumer  first producer
// pierwszy producent czeka na locku ale zwolnił first

// f-first r-rest l-lock w-waiting
//---------10-------------
//    fw rw lw portion
// c1
// c2
//    fw rw lw portion
// p1 -          2
// p2
//---------10-------------
//    fw rw lw portion
// c1
// c2
//    fw rw lw portion      gdy konsument skonsumował p2 czekało na locku, poszło signal do p1 więc p1 też na locku.
// p1       -               p2 weszło do monitora, zobaczyło że firstProd jest puste ale nie ma miejsca w buforze
// p2       -               więc dostało się do first prod, p1 wchodzi pętla go wrzuca na firstProd, mamy p1 i p2 na firstProd

// załóżmy, że na firstProd czeka 10xp chcące wyprdukować 2
// teraz konsumenci konsumują po 5
// tak długo aż zaiwiesimy 1 na firstCons i 2 na otherCons
// wchodzi producent produkuje odwiesza first cons ( które za chwilę znów się powiesi bo wyprodukowane zostało za mało)
// ale w między czasie wchodzi nowy konsument i wiesza się na firstCons bo ten co był wcześniej czeka na locku

// mamy 2 konsumentów na first

//1. doprowadź do sytuacji: "więcej niż jeden wątek na firstProd" (bufor prawie pełny)
//
//2. wprowadź wątki konsumentów, by opróżniły bufor (jak najmniejszą ich liczbą, by na firstProd został jakiś wątek nadmiarowo tam uwięzione wątki) (bufor pusty)
//
//3. dalej wprowadzaj watki konsumentów - tym razem przy pustym buforze zaczna sie wieszac na firstCons i na othersCons
//
//4. doprowadz do zawieszenia sie wiecej niz jednego konsunenta na firstCons
//
//5. (stan po poprzednich krokach jest taki: bufor pusty, konsumenci wiszą, na firstProd został jeden z producentów) wpuszczamy producenta..... który warunek sprawdza?....