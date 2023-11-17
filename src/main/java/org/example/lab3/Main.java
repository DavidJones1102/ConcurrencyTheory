package org.example.lab3;

import org.example.lab3.Buffer.*;

public class Main {
    public static void main(String[] args) {
        IBuffer status = new Buffer3Lock(10);

//        for (int i=0;i<5;i++){
//         Producer producer = new Producer(status, i);
//         producer.start();
//        }
//
//        for (int i=0;i<5;i++){
//         Consumer consumer = new Consumer(status, i);
//         consumer.start();
//        }
         Producer p1 = new Producer(status, 1, 1);
         p1.start();
         Producer p3 = new Producer(status, 2, 4);
         p3.start();
         Consumer c1 = new Consumer(status, 1, 1);
         c1.start();
         Consumer c2 = new Consumer(status, 2, 4);
         c2.start();
    }
}

// max 10 stan 0
// produce 7 stan 7  wp         wc
// produce 4 stan 7  wp 4       wc
// consume 8         wp 4       wc 8
// produce 8         wp 4 8     wc 8