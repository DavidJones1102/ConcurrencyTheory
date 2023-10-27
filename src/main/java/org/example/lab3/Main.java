package org.example.lab3;

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
