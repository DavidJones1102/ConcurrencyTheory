package org.example.lab3;

import org.example.lab3.Buffer.IBuffer;

public class Producent extends Thread{
    IBuffer buffer;
    final int id;
    public Producent(IBuffer c, int id){
        buffer = c;
        this.id = id;
    }

    @Override
    public void run() {
        while (true){
            buffer.produce(Random.getRandomNumber(0, buffer.getLimit()/2));
        }
    }
}

