package com.example.shopberry.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvUtils {

    public static <T> List<T> loadFromCsv(String path, int expectedColumns, Function<String[], T> mapper) throws IOException {
        InputStream inputStream = CsvUtils.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + path);
        }

        List<T> result = new ArrayList<>();
        try (Reader reader = new InputStreamReader(inputStream)) {
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            try (CSVParser csvParser = new CSVParser(reader, format)) {
                for (CSVRecord csvRecord : csvParser) {
                    String[] parts = new String[expectedColumns];
                    for (int i = 0; i < expectedColumns; i++) {
                        parts[i] = csvRecord.get(i);
                    }
                    result.add(mapper.apply(parts));
                }
            }
        }

        return result;
    }

}
