package com.example.student.async;

/**
 * Created by student on 14.04.16.
 */
public interface ProgressUpdater<T> {
    void update(T output);
}
