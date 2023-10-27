package org.example.lab3;

import org.example.lab3.Buffer.BufferStarve;
import org.example.lab3.Buffer.IBuffer;

public class Main {
    public static void main(String[] args) {
        IBuffer status = new BufferStarve(10);

        for (int i=0;i<5;i++){
         Producent producent = new Producent(status, i);
         producent.start();
        }

        for (int i=0;i<5;i++){
         Consumer consumer = new Consumer(status, i);
         consumer.start();
        }
    }
}

// max 10 stan 0
// produce 7 stan 7  wp         wc
// produce 4 stan 7  wp 4       wc
// consume 8         wp 4       wc 8
// produce 8         wp 4 8     wc 8