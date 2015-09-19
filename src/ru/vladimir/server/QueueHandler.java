package ru.vladimir.server;

import com.mysql.jdbc.Statement;
import org.apache.commons.io.IOUtils;
import ru.vladimir.model.Measurement;
import ru.vladimir.worker.MessageQueue;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
            if (queue.size() >= 4000) {

                Statement ldstmt = null;
                String statementText = "LOAD DATA LOCAL INFILE 'file.txt'" +
                        "INTO TABLE measurements " +
                        "FIELDS TERMINATED BY \',\'" +
                        "(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir);";
                try {
                    ldstmt = (com.mysql.jdbc.Statement) connection.createStatement();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int size = queue.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    Measurement m = queue.receive();
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
                    builder.append(',');
                    builder.append('\n');
                }
                InputStream is = IOUtils.toInputStream(builder.toString());
                ldstmt.setLocalInfileInputStream(is);
                try {
                    ldstmt.execute(statementText);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
