package pl.pk;

import lombok.extern.java.Log;
import pl.pk.commandline.CommandLineInputParser;
import pl.pk.commandline.CommandLineParameters;
import pl.pk.processor.FileProcessor;

@Log
public class TextConverterApplication {
  public static void main(String[] args) {
    CommandLineParameters params;
    try {
      params = CommandLineInputParser.parse(args);
      FileProcessor.process(params);
    } catch (Exception e) {
      log.severe(e.getMessage());
      return;
    }
  }
}
