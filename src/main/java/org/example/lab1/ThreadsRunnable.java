package org.example.lab1;

public class ThreadsRunnable implements Runnable {
    int num;
    public void ThreadRunnable(int num){
        this.num = num;
    }
    @Override
    public void run() {
        System.out.println("Welcome from thread!");
    }
}
