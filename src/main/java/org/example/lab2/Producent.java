package org.example.lab2;

public class Producent extends Thread{
    Status status;
    public Producent(Status c){
        status = c;
    }

    @Override
    public void run() {
        while (true){
            status.setToReady();
        }
    }
}

