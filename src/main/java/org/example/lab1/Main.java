package org.example.lab1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var c = new Counter();
        int t = 10000;
        int incrementThreads = 4;
        int decrementThreads = 4;
//        Thread[incrementThreads+decrementThreads] threads;
        for (int i=0;i<incrementThreads;i++){
            var incrementor = new Incrementor(c, t);
            incrementor.start();
//            threads.appe
        }
        var d = new Decrementor(c, t);
        d.start();
//        i.join();
        d.join();
        System.out.println(c.getCounter());
        Scanner keyboard = new Scanner(System.in);

    }
}

