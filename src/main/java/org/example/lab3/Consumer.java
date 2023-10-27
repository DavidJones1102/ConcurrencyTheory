package org.example.lab3;

import org.example.lab3.Buffer.IBuffer;

public class Consumer extends Thread {
    IBuffer buffer;
    final int id;
    public Consumer(IBuffer c, int id){
        buffer = c;
        this.id = id;
    }

    @Override
    public void run() {
        while (true){
            buffer.consume(Random.getRandomNumber(0, buffer.getLimit()/2));
        }
    }
}
