package org.example.lab1;

public class Incrementor extends Thread{
    Counter c;
    int t;
    public Incrementor(Counter c, int t){
        this.c = c;
        this.t = t;
    }
    public void run(){
        for (int i=0; i<t;i++){
        c.increment();
        }
    }
}
