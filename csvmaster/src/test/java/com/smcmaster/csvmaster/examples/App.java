package com.smcmaster.csvmaster.examples;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import com.smcmaster.csvmaster.CSVMaster;
import com.smcmaster.csvmaster.Row;

/**
 * Simple test app for the CSVMaster CSV processor.
 */
public class App {
  public static void main(String[] args) throws Exception {
    String filename = "src/test/resources/Sacramentorealestatetransactions.csv";
    Reader in = new FileReader(filename);
    CSVMaster master = new CSVMaster();
    try (CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader())) {
      master.loadFrom(parser);
    }
    // Change each zip value to a default zip+4.
    master.forEach(row -> row.set("zip", row.get("zip") + "-0000"));
    master.forEach(row -> System.out.println(row.get("zip")));

    // Get all of the rows from a specific zip code.
    List<Row> specialRows = master.stream().filter(row -> row.get("zip").startsWith("95610"))
        .collect(Collectors.toList());
    specialRows.forEach(row -> System.out.println(row.get("street")));

    master.writeTo(System.out);
  }
}
