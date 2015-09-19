package ru.vladimir.util;

/**
 * Created by roelof on 17/09/2015.
 */
public interface Channel<E> {

    public void send(E item);

    public E receive();
}
