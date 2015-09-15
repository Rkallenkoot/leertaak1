package ru.vladimir;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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
                            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            StringBuilder sb = new StringBuilder();
                            String inline = "";

                            while ((inline = input.readLine()) != null) {
                                sb.append(inline);
                            }

                            SAXBuilder builder = new SAXBuilder();

                            Document doc = builder.build(new ByteArrayInputStream(sb.toString().getBytes()));

                            System.out.println(doc.toString());
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
