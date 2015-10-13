package ru.vladimir.database;

import java.sql.*;
import java.util.Properties;

import org.relique.jdbc.csv.CsvDriver;

public class DbDriver {
    Connection conn;

    public DbDriver(String directory) {
        loadDriver(directory);

    }

    private void loadDriver(String directory){
        try {
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");

            // Create a connection. The first command line parameter is
            // the directory containing the .csv files.
            // A single connection is thread-safe for use by several threads.
            conn = DriverManager.getConnection("jdbc:relique:csv:" + directory);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doQuery(String query){
        try {
            // Create a Statement object to execute the query with.
            // A Statement is not thread-safe.
            Statement stmt = conn.createStatement();

            // Select the ID and NAME columns from sample.csv
            ResultSet results = stmt.executeQuery(query);

            // Dump out the results to a CSV file with the same format
            // using CsvJdbc helper function
            boolean append = true;
            CsvDriver.writeToCsv(results, System.out, append);

            // Clean up
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




}
