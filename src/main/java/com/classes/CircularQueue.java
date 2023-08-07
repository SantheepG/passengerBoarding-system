package com.classes;

import java.util.Arrays;

public class CircularQueue<T> {

    /**
     * Circular queue to enqueue, dequeue passengers after cabins are filled
     * implemented to enqueue passenger after cabins are filled
     * passenger will be dequeued after a deletion of a person from a cabin
     *
     */
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private T[] array;

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.front = -1;
        this.rear = -1;
        this.size = 0;
        this.array = (T[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public void enqueue(T item) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue more elements.");
            return;
        }

        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }

        array[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }

        T dequeuedItem = array[front];
        array[front] = null;

        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }

        size--;
        return dequeuedItem;
    }

    public T front() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        return array[front];
    }

    public T rear() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        return array[rear];
    }

    @Override
    public String toString() {
        return "Queue :" +
                 Arrays.toString(array) ;
    }
}
