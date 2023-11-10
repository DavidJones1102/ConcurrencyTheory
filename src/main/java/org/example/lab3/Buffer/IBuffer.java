package org.example.lab3.Buffer;

public interface IBuffer {
    void produce(int portion, int id) throws InterruptedException;
    void consume(int portion, int id) throws InterruptedException;
    int getLimit();
}
