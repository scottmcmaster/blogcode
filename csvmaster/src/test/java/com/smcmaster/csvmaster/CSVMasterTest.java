package com.smcmaster.csvmaster;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link CSVMaster} class.
 * 
 * @author smcmaster
 */
public class CSVMasterTest {
  
  private String csv;
  
  @Before
  public void setUp() {
    csv = "Name,Address,Zip\n";
    csv += "Scott M,101 Main Street,90210\n";
    csv += "Bob S,Fourth Avenue,11111\n";
  }
  
  @Test
  public void testLoadFrom() throws Exception {
    CSVMaster master = loadMaster();
    assertEquals(2, master.getRows().size());
  }

  @Test
  public void testWriteTo() throws Exception {
    CSVMaster master = loadMaster();
    StringWriter writer = new StringWriter();
    master.writeTo(writer);
    assertEquals(csv, writer.toString());
  }
  
  @Test
  public void testAddRow() throws Exception {
    CSVMaster master = loadMaster();
    assertEquals(2, master.getRows().size());
    Row newRow = master.addRow();
    assertEquals(3, master.getRows().size());
    newRow.set("Name", "a");
    newRow.set("Address", "b");
    newRow.set("Zip", "c");

    StringWriter writer = new StringWriter();
    master.writeTo(writer);
    assertTrue(writer.toString().startsWith(csv));
    assertTrue(writer.toString().endsWith("a,b,c\n"));
  }
  
  private CSVMaster loadMaster() throws Exception, IOException {
    CSVMaster master = new CSVMaster();
    try (CSVParser parser = new CSVParser(new StringReader(csv),
        CSVFormat.EXCEL.withHeader())) {
      master.loadFrom(parser);
    }
    return master;
  }
}
