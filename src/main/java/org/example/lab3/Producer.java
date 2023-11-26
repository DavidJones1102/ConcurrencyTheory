package org.example.lab3;

import org.example.lab3.Buffer.IBuffer;


public class Producer extends Thread{
    IBuffer buffer;
    final int id;
    private final int constPortion;
    private int timesProduced = 0;
    private java.util.Random random ;
    public Producer(IBuffer c, int id){
        this(c, id, 0);
    }
    public Producer(IBuffer c, int id, int constPortion){
        this.constPortion = constPortion;
        buffer = c;
        this.id = id;
        this.random = new java.util.Random(id);
    }
    @Override
    public void run() {
        int portion;
        while (!Thread.currentThread().isInterrupted()){
            if (constPortion == 0){
                portion = random.nextInt(0,buffer.getLimit()/2);
            }else{
                portion = constPortion;
            }

            try {
                buffer.produce(portion, id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            timesProduced++;
//            System.out.println("Producer id: "+id+", portion: "+portion+", times produced: "+timesProduced);
        }
    }
}

