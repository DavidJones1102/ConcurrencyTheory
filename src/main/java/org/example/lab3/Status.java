package org.example.lab3;

public class Status {
    private int counter = 0;
    public synchronized void setToReady() {
        while (counter == 1){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter = 1;
        System.out.println("Produce ");
        notifyAll();
    }
    public synchronized void setToTaken() {
        while (counter == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter = 0;
        System.out.println("Consume ");
        notifyAll();
    }
}
