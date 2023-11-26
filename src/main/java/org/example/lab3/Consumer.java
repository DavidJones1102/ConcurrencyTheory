package org.example.lab3;

import org.example.lab3.Buffer.IBuffer;

public class Consumer extends Thread {
    IBuffer buffer;
    final int id;
    private final int constPortion;
    private int timesConsumed = 0;
    private java.util.Random random;
    private int lifeTime;
    public Consumer(IBuffer c, int id){
        this(c,id,0, 500);
    }
    public Consumer(IBuffer c, int id, int lifeTime){
        this(c,id,0, lifeTime);
    }

    public Consumer(IBuffer c, int id, int constPortion, int lifeTime){
        buffer = c;
        this.id = id;
        this.constPortion = constPortion;
        this.random = new java.util.Random(id);
        this.lifeTime = lifeTime;
    }

    @Override
    public void run() {
        int portion;
        for (int i=0;i<lifeTime;i++){
            if (constPortion == 0){
                portion = random.nextInt(0, buffer.getLimit()/2);
            }else {
                portion = constPortion;
            }
            try {
                buffer.consume(portion, id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            timesConsumed++;
//            System.out.println("Consumer id: "+id+", portion: "+portion+", times consumed: "+timesConsumed);
        }

    }
}
