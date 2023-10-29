package org.example.lab3.Buffer;

public interface IBuffer {
    void produce(int portion, int id);
    void consume(int portion, int id);
    int getLimit();
}
