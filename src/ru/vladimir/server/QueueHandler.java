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
            if (queue.size() >= 1) {

                Statement ldstmt = null;
                String statementText = "LOAD DATA LOCAL INFILE 'file.txt'" +
                        "INTO TABLE measurements " +
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
                    builder.append("stn");
                    builder.append('\t');
                    builder.append(m.getStn());
                    builder.append('\n');

                    builder.append("date\t");
                    builder.append(m.getDate());
                    builder.append('\n');

                    builder.append("time\t");
                    builder.append(m.getTime());
                    builder.append('\n');

                    builder.append("temp\t");
                    builder.append(m.getTemp());
                    builder.append('\n');

                    builder.append("dewp\t");
                    builder.append(m.getDewp());
                    builder.append('\n');

                    builder.append("stp\t");
                    builder.append(m.getStp());
                    builder.append('\n');

                    builder.append("slp\t");
                    builder.append(m.getSlp());
                    builder.append('\n');

                    builder.append("visib\t");
                    builder.append(m.getVisib());
                    builder.append('\n');

                    builder.append("wdsp\t");
                    builder.append(m.getWdsp());
                    builder.append('\n');

                    builder.append("prcp\t");
                    builder.append(m.getPrcp());
                    builder.append('\n');

                    builder.append("sndp\t");
                    builder.append(m.getSndp());
                    builder.append('\n');

                    builder.append("frshtt\t");
                    builder.append(m.getFrshtt());
                    builder.append('\n');

                    builder.append("cldc\t");
                    builder.append(m.getCldc());
                    builder.append('\n');

                    builder.append("wnddir\t");
                    builder.append(m.getWnddir());
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
