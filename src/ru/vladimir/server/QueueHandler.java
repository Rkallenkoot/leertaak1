package ru.vladimir.server;

import ru.vladimir.model.Measurement;
import ru.vladimir.worker.MessageQueue;

/**
 * Created by roelof on 19/09/2015.
 */
public class QueueHandler implements Runnable {

    private MessageQueue<Measurement> queue;

    /**
     * Initializes the QueueHandler with the given Queue
     *
     * @param queue
     */
    public QueueHandler(MessageQueue<Measurement> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // Wat moet de queue handler doen??
        while (true) {
            if (queue.size() > 8000) {
                // Onmogelijk dat die null is????
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Measurement m = queue.receive();

                }
            }
        }
    }

}
