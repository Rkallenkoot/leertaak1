package ru.vladimir.server;

import ru.vladimir.model.Measurement;
import ru.vladimir.worker.MessageQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by roelof on 19/09/2015.
 */
public class QueueHandler implements Runnable {

    private final static String url = "jdbc:mysql://192.168.0.39:3306/unwdmi";
    private final static String user = "unwdmi";
    private final static String pass = "dUVFLAj97dFeMnZM";
    private static final String SQL = "INSERT INTO measurements " +
            "(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private Connection connection;
    private MessageQueue<Measurement> queue;

    /**
     * Initializes the QueueHandler with the given Queue
     *
     * @param queue
     */
    public QueueHandler(MessageQueue<Measurement> queue) {
        this.queue = queue;

        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Wat moet de queue handler doen??
        while (true) {
            if (queue.size() >= 8000) {

                PreparedStatement stmt = null;
                try {
                    stmt = connection.prepareStatement(SQL);
                    connection.setAutoCommit(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Measurement m = queue.receive();
                    try {
                        stmt.setInt(1, m.getStn());
                        stmt.setDate(2, m.getDate());
                        stmt.setTime(3, m.getTime());
                        stmt.setFloat(4, m.getTemp());
                        stmt.setFloat(5, m.getDewp());
                        stmt.setFloat(6, m.getStp());
                        stmt.setFloat(7, m.getSlp());
                        stmt.setFloat(8, m.getVisib());
                        stmt.setFloat(9, m.getWdsp());
                        stmt.setFloat(10, m.getPrcp());
                        stmt.setFloat(11, m.getSndp());
                        stmt.setByte(12, m.getFrshtt());
                        stmt.setFloat(13, m.getCldc());
                        stmt.setShort(14, m.getWnddir());
                        stmt.addBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    // Save return values in int[] count en commit
                    int[] count = stmt.executeBatch();
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
