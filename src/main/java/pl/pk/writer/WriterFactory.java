package pl.pk.writer;

import java.io.Writer;

public class WriterFactory {

  public static FileWriter createXmlWriter(Writer os) {
    return new XmlWriter(os);
  }

  public static FileWriter createCsvWriter(Writer os) {
    return new CsvWriter(os);
  }
}
