package ru.vladimir.client;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseReader {

    public static void main(String[] args) {
        DatabaseReader.doQuery();
    }

    public static void doQuery() {
        try {
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            Properties props = new Properties();
            props.put("fileExtension", ".csv");
            props.put("indexedFiles", "true");
            props.put("fileTailPattern", "_(\\d+)");
////            props.put("fileTailParts", "station");

            props.put("suppressHeaders", "true");

            // 15e column added omdat die kut library zeikt over 15 gevonden met maar 14 column headers
            // KUT library
            props.put("headerline", "stn,date,time,temp,dewp,stp,slp,visib,wdsp,prcp,sndp,frshht,cldc,wnddir,stationIndex");
            props.put("columnsTypes", "Int,Date,Time,float,float,float,float,float,float,float,float,float,byte,short,String");
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:db/measurements", props);
            Statement stmt = conn.createStatement();
            File f = new File("./db/measurements");
            String[] dirArray = f.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches("[0-9]+");
                }
            });
            String dirString = String.join(",", dirArray);
            String sql = "SELECT * FROM measurements"; //+ dirString;

            ResultSet results = stmt.executeQuery(sql);
            while (results.next()) {
                System.out.println(results.getInt(1));
                System.out.println(results.getString(2));
                System.out.println(results.getString(15));
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

