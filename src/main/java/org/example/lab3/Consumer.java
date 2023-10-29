package org.example.lab3;

import org.example.lab3.Buffer.IBuffer;

public class Consumer extends Thread {
    IBuffer buffer;
    final int id;
    private final int constPortion;
    public Consumer(IBuffer c, int id){
        this(c,id,0);
    }

    public Consumer(IBuffer c, int id, int constPortion){
        buffer = c;
        this.id = id;
        this.constPortion = constPortion;
    }

    @Override
    public void run() {
        int portion;
        while (true){
            if (constPortion == 0){
                portion = Random.getRandomNumber(0, buffer.getLimit()/2);
            }else {
                portion = constPortion;
            }
            buffer.consume(portion, id);
        }
    }
}
