package ru.vladimir.worker;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.vladimir.model.Measurement;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Worker implements Runnable {

    private Socket socket;
    private MessageQueue<Measurement> queue;

    public Worker(Socket socket, MessageQueue<Measurement> queue) {
        this.socket = socket;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Define a schema factory and a schema
//            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = schemaFactory.newSchema(new File("./resources/weatherdata.dtd"));

            // create an XMLReaderJDOMFactory by passing the schema
//            XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
            // create a SAXBuilder using the XMLReaderJDOMFactory
            SAXBuilder builder = new SAXBuilder();
            StringBuilder sb = new StringBuilder();
            String inline = "";

            while ((inline = input.readLine()) != null) {
                sb.append(inline.trim());
                if (inline.equals("</WEATHERDATA>")) {
                    Document doc = builder.build(new ByteArrayInputStream(sb.toString().getBytes()));
                    List<Element> elements = doc.getRootElement().getChildren("MEASUREMENT");
                    // Validate each measurement
                    // TODO: Hier uitzoeken of fucking java wel echt pass by Reference is kek
                    // Als dat wel zo is hoeven we elements niet te reassignen maar kunnen we
                    // de values in de Validator aanpassen.
                    for (Element e : elements) {
                        queue.send(new Measurement(e));
                    }
                    // Validaten de QueueHandler laten doen ??
//                    Validator.validateWeatherData(elements);

                    // push them into the Queue
                    sb.setLength(0);
                }
            }

            input.close();
            input = null;
        } catch (NullPointerException | IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } catch (JDOMException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
