package com.smcmaster.csvmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Main CSV processor, supports loading from an Apache Commons CSV parser
 * and exposes the CSV file via keyed data structures.
 * 
 * @author smcmaster
 */
public class CSVMaster implements Iterable<Row> {

  private List<Row> rows = new ArrayList<>();
  private List<String> header;

  public void loadFrom(CSVParser parser) throws Exception {
    this.header = new ArrayList<String>(parser.getHeaderMap().keySet());
    for (CSVRecord record : parser.getRecords()) {
      rows.add(Row.loadFrom(parser.getHeaderMap(), record));
    }
  }

  public List<Row> getRows() {
    return rows;
  }

  @Override
  public Iterator<Row> iterator() {
    return rows.iterator();
  }

  public Stream<Row> stream() {
    return rows.stream();
  }

  public void writeTo(Appendable out) throws IOException {
    for (int i = 0; i < header.size(); i++) {
      out.append(header.get(i));
      if (i < header.size() - 1) {
      }
    }
    out.append('\n');
    for (Row row : rows) {
      for (int i = 0; i < header.size(); i++) {
        out.append(row.get(header.get(i)));
        if (i < header.size() - 1) {
          out.append(',');
        }
      }
      out.append('\n');
    }
  }
}
