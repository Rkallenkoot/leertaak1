package ru.vladimir;

import ru.vladimir.client.DatabaseReader;

public class ClientMain {

    public static void main(String[] args) {

        DatabaseReader client = new DatabaseReader("./db", "measurements");

        System.out.println(client.getStation(10010));

    }
}
