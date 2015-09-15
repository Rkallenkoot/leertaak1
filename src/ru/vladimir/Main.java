package ru.vladimir;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            ServerSocket serverSocket = new ServerSocket(6050);
            while (true) {
                Socket socket = serverSocket.accept();

                new Thread() {
                    public void run() {
                        try {
                            System.out.println(System.currentTimeMillis());
                            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            SAXBuilder builder = new SAXBuilder();
                            StringBuilder sb = new StringBuilder();
                            String inline = "";

                            while ((inline = input.readLine()) != null) {
                                sb.append(inline);
                                if (inline.equals("</WEATHERDATA>")) {
//                                    System.out.println(System.currentTimeMillis());
                                    Document doc = (Document) builder.build(new ByteArrayInputStream(sb.toString().getBytes()));
                                    System.out.println(new XMLOutputter().outputString(doc));
                                    sb.setLength(0);
                                }
                            }

                        } catch (IOException ioEx) {
                            System.out.println(ioEx.getMessage());
                        } catch (JDOMException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }
    }
}
