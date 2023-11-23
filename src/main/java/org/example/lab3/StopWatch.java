package org.example.lab3;

public class StopWatch {
    private long time;
    private long start;
    boolean running = false;
    public void start(){
        if (running){
            System.exit(11);
        }
        running = true;
        start = System.currentTimeMillis();
    }
    public void stop(){
        if (!running){
            System.exit(11);
        }
        time += System.currentTimeMillis()-start;
        start = 0;
        running = false;
    }
    public long reset(){
        long endTime = time;
        time=0;
        return endTime;
    }
    public long getTime() {
        return time;
    }
}
