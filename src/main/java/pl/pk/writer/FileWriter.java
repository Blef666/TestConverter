package pl.pk.writer;

import pl.pk.model.Sentence;

import java.util.Map;

public interface FileWriter {

  void write(Map<Sentence, Void> sentence);

  void close();
}
