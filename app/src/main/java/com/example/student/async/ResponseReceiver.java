package com.example.student.async;

/**
 * Created by student on 14.04.16.
 */
public interface ResponseReceiver<T> {
    void respond(T output);
}
