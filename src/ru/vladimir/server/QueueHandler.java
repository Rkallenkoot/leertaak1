package ru.vladimir.server;

import ru.vladimir.database.DatabaseWriter;
import ru.vladimir.model.Measurement;
import ru.vladimir.validation.Validator;
import ru.vladimir.worker.MessageQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueHandler implements Runnable {

    private MessageQueue<Measurement> queue;
    private DatabaseWriter databaseWriter;
    private Validator validator;
    private Map<Integer, List<byte[]>> output;

    /**
     * Initializes the QueueHandler with the given Queue
     *
     * @param queue
     */
    public QueueHandler(MessageQueue<Measurement> queue, DatabaseWriter writer) {
        this.queue = queue;
        this.validator = new Validator();
        this.output = new HashMap<>();
        this.databaseWriter = writer;
    }

    @Override
    public void run() {
        while (true) {
            if (queue.size() >= 8000) {
                int size = queue.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    Measurement m = validator.validateMeasurement(queue.receive());

                    builder.append(m.getStn());
                    builder.append(',');

                    builder.append(m.getDate());
                    builder.append(',');

                    builder.append(m.getTime());
                    builder.append(',');

                    builder.append(m.getTemp());
                    builder.append(',');

                    builder.append(m.getDewp());
                    builder.append(',');

                    builder.append(m.getStp());
                    builder.append(',');

                    builder.append(m.getSlp());
                    builder.append(',');

                    builder.append(m.getVisib());
                    builder.append(',');

                    builder.append(m.getWdsp());
                    builder.append(',');

                    builder.append(m.getPrcp());
                    builder.append(',');

                    builder.append(m.getSndp());
                    builder.append(',');

                    builder.append(m.getFrshtt());
                    builder.append(',');

                    builder.append(m.getCldc());
                    builder.append(',');

                    builder.append(m.getWnddir());
                    builder.append('\n');

                    List<byte[]> list = output.get(m.getStn()) == null ? new ArrayList<byte[]>() : output.get(m.getStn());
                    list.add(builder.toString().getBytes());
                    output.put(m.getStn(), list);
                    builder.setLength(0);
                }
                databaseWriter.writeMap("measurements", output);
                output.clear();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
