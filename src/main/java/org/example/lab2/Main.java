package org.example.lab2;

public class Main {
    public static void main(String[] args) {
        Status status = new Status();

        for (int i=0;i<5;i++){
         Producent producent = new Producent(status);
         producent.start();
        }

        for (int i=0;i<5;i++){
         Consumer consumer = new Consumer(status);
         consumer.start();
        }
    }
}

// B P1 P2 C1 C2
// 0 s  s  s  s
// 1 p  s  w  w
// 0 s  w  c  s
// 1 s  w  c  s