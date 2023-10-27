package org.example.lab3;

public class Consumer extends Thread {
    Status status;
    public Consumer(Status c){
        status = c;
    }

    @Override
    public void run() {
        while (true){
            status.setToTaken();
        }
    }
}
