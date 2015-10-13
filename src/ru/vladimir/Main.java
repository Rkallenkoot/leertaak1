package ru.vladimir;

import ru.vladimir.database.DbDriver;
import ru.vladimir.server.Server;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //new Server();
        DbDriver driver = new DbDriver("db/measurements");
        driver.doQuery("SELECT * FROM 10080/2015-10-13");

    }
}
