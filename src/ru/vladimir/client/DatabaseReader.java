package ru.vladimir.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.vladimir.model.Measurement;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseReader {


    private String dbDirectory;
    private String tableName;

    public DatabaseReader(String dbDirectory, String tableName) {
        this.dbDirectory = dbDirectory;
        this.tableName = tableName;
    }

    /**
     * @param stn Stationnumber
     * @returns String json output of measurements
     */
    public String getStation(int stn) {
        String jsonOutput = "";
        Path stnDir = Paths.get(String.format("%s/%s/%s", dbDirectory, tableName, stn));
        // Check of we data hebben van betreffende stn
        if (!Files.exists(stnDir)) {
            // TODO: MAAK HIER EXCEPTION VOOR
            return jsonOutput;
        }

        //2015-10-15 dateformat
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        String dateFileDir = String.format("%s/%s.csv", stnDir, sdf.format(date));
        Path dateFile = Paths.get(dateFileDir);
        if (!Files.exists(dateFile)) {
            // TODO: THROW EXCEPTION
            return jsonOutput;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Reader in = new FileReader(dateFileDir);
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("stn", "date", "time", "temp", "dewp", "stp", "slp", "visib", "wdsp", "prcp", "sndp", "frshtt", "cldc", "wnddir");
            CSVParser csvFileParser = new CSVParser(in, csvFormat);
            List csvRecords = csvFileParser.getRecords();
            List<Measurement> measurements = new ArrayList<>();
            for (int i = 0; i < csvRecords.size(); i++) {
                CSVRecord rec = (CSVRecord) csvRecords.get(i);
                Measurement m = new Measurement(rec);
                measurements.add(m);
            }
            jsonOutput = gson.toJson(measurements);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonOutput;
    }
}

