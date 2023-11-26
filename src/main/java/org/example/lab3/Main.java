package org.example.lab3;

import org.example.lab3.Buffer.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        LinkedList<Producer> producers = new LinkedList<>();
        LinkedList<Consumer> consumers = new LinkedList<>();
        StringBuilder results = new StringBuilder();
        int[] consumerLifeTimeArray = {100, 500, 1000, 1500, 2000, 5000, 10_000, 100_000, 1_000_000};

        for (int consumerLifeTime: consumerLifeTimeArray){
            IBuffer status = new Buffer4Conditions2Bool(10);

            for (int i=0;i<5;i++){
                producers.add( new Producer(status, i));
                consumers.add( new Consumer(status, i, consumerLifeTime));
            }
            producers.forEach(Thread::start);

            long startTime = System.currentTimeMillis();
            consumers.forEach(Thread::start);
            consumers.forEach(c -> {
                try {
                    c.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();;
                }
            });
            long time = System.currentTimeMillis()-startTime;

            // clear
            producers.forEach(Thread::interrupt);
            producers.clear();
            consumers.clear();
            results.append(consumerLifeTime).append(",").append(time).append("\n");
            // save

        }
        PrintWriter save = new PrintWriter("results_4_conditions.txt");
        save.println(results);
        save.close();
        System.exit(0);

//         Producer p1 = new Producer(status, 1, 1);
//         p1.start();
//         Producer p3 = new Producer(status, 2, 4);
//         p3.start();
//         Consumer c1 = new Consumer(status, 1, 1);
//         c1.start();
//         Consumer c2 = new Consumer(status, 2, 4);
//         c2.start();
    }
}

// max 10 stan 0
// produce 7 stan 7  wp         wc
// produce 4 stan 7  wp 4       wc
// consume 8         wp 4       wc 8
// produce 8         wp 4 8     wc 8