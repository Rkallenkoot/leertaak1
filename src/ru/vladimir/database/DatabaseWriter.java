package ru.vladimir.database;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DatabaseWriter {

    private String directory;
    private List<Integer> stations;

    public DatabaseWriter(String directory) {
        this.directory = directory;
        setStations();
        System.out.println("ASDIUADIUASBDIUBASD: " + stations.size());
        // create die directory
        Path p = Paths.get(directory);
        if (!Files.exists(p)) {
            try {
                Files.createDirectories(p);
            } catch (IOException e) {
                System.out.println("Unable to initialize DatabaseWriter: " + e.getMessage());
            }
        }
    }

    private void setStations() {
        String stationsFileDir = String.format("%s/stations/stations.csv", directory);
        Reader in = null;
        try {
            in = new FileReader(stationsFileDir);
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("stn", "name", "country", "latitude", "longtitude", "elevation");
            CSVParser csvFileParser = new CSVParser(in, csvFormat);
            List csvRecords = csvFileParser.getRecords();
            stations = new Vector<Integer>();
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = (CSVRecord) csvRecords.get(i);
                stations.add(Integer.parseInt(record.get("stn")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeMap(String tableName, Map<Integer, List<byte[]>> outputMap) {
        // check if tableName folder exists
        Path p = Paths.get(directory + "/" + tableName);
        if (!Files.exists(p)) {
            // directory aanmaken
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                System.out.println("Unable to create TableName directory in DatabaseWriter: " + e.getMessage());
            }
        }


        // we writen die shit in tableName directory en pleuren het in de csvFieldFileName
        // parse naar csv
        // haal date op "offset 1 bijv"
        // check of directory van stationnummer bestaat, pak offset[1] ( date ) en check of die file bestaat

        for (Map.Entry<Integer, List<byte[]>> entry : outputMap.entrySet()) {
            if (!stations.contains(entry.getKey())) {
                continue;
            }
            Path path = Paths.get(String.format("%s/%s/%s", directory, tableName, entry.getKey()));
            if (!Files.exists(path)) {
                // als die niet bestaat moeten we die folder aanmaken
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    System.out.println("Unable to create Directory: " + entry.getKey());
                }
            }
            // haal datum op
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(entry.getValue().get(0))));
            String line = "";
            String date = null;
            try {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    date = values[1];
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            final File file = new File(String.format("%s/%s/%s/%s.csv", directory, tableName, entry.getKey(), date));
            // calculate the appendSize
            long appendSize = 0;
            for (byte[] arr : entry.getValue()) {
                appendSize += arr.length;
            }

            try {
                final RandomAccessFile raf = new RandomAccessFile(file, "rw");

                raf.seek(raf.length());
                final FileChannel fc = raf.getChannel();
                final MappedByteBuffer mbf = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), appendSize);
                fc.close();

                // pleur die rommel in die mbf
                for (byte[] bitjes : entry.getValue()) {
                    mbf.put(bitjes);
                }
                raf.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
