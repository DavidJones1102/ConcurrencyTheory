package org.example.lab3.Buffer;

public interface IBuffer {
    void produce(int portion);
    void consume(int portion);
    int getLimit();
}
