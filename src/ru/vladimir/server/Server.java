package ru.vladimir.server;

import ru.vladimir.model.Measurement;
import ru.vladimir.worker.MessageQueue;
import ru.vladimir.worker.Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int port = 6050;
    private static final int poolSize = 1680;

    private ServerSocket serverSocket;
    private ExecutorService pool;
    private MessageQueue<Measurement> queue;
    private QueueHandler queueHandler;

    /**
     * Default Constructor
     */
    public Server() {
        try {
            this.serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(poolSize);
            queue = new MessageQueue<>();
            queueHandler = new QueueHandler(queue);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        runServer();
    }

    /**
     * Full Constructor
     *
     * @param port port the server should listen on
     */
    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(poolSize);
            queue = new MessageQueue<>();
            queueHandler = new QueueHandler(queue);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        runServer();
    }

    public void runServer() {
        Thread thread = new Thread(queueHandler);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start(); // queueHandler is running

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                // Hier Worker aanmaken en socket meegeven
                pool.execute(new Worker(socket, queue));

            } catch (IOException e) {
                pool.shutdown();
                System.out.println(e.getMessage());
            }
        }
    }


}
