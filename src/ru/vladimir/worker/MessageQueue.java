package ru.vladimir.worker;

import ru.vladimir.util.Channel;

import java.util.Vector;

/**
 * Created by roelof on 17/09/2015.
 */
public class MessageQueue<E> implements Channel<E> {

    private Vector<E> queue;

    public MessageQueue() {
        queue = new Vector<>();
    }

    public void send(E item) {
        queue.add(item);
    }

    public E receive() {
        if (queue.size() == 0) {
            return null;
        } else {
            return queue.remove(0);
        }
    }

    public int size() {
        return queue.size();
    }
}
