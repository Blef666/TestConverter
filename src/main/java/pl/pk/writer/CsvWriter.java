package pl.pk.writer;

import com.opencsv.CSVWriter;
import pl.pk.model.Sentence;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CsvWriter implements FileWriter {

  private CSVWriter writer;
  private long lineNumber = 0;

  public CsvWriter(Writer os) {
    writer =
        new CSVWriter(
            os,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);
    List<String> headers =
        Stream.iterate(0, n -> n + 1)
            .limit(1000)
            .map(n -> "Word " + n)
            .collect(Collectors.toList());
    headers.add(0, "");

    writer.writeNext(headers.toArray(new String[0]));
  }

  @Override
  public void write(Map<Sentence, Void> sentence) {
    List<String> words = sentence.keySet().iterator().next().getWords();
    words.add(0, "Sentence " + ++lineNumber);
    writer.writeNext(words.toArray(new String[0]));
  }

  @Override
  public void close() {}
}
