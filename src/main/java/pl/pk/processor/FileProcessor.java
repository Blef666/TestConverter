package pl.pk.processor;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import pl.pk.commandline.CommandLineParameters;
import pl.pk.model.Sentence;
import pl.pk.writer.FileWriter;
import pl.pk.writer.WriterFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
public class FileProcessor {

  public static void process(CommandLineParameters params) {
    try (BufferedReader br =
            new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(params.getInputFilePath()), StandardCharsets.UTF_8));
        OutputStreamWriter os =
            new OutputStreamWriter(
                new FileOutputStream(params.getOutputFilePath()), StandardCharsets.UTF_8)) {
      log.info(String.format("Processing of file %s started", params.getInputFilePath()));
      FileWriter writer = null;
      switch (params.getOutputFileType()) {
        case csv:
          writer = WriterFactory.createCsvWriter(os);
          break;
        case xml:
          writer = WriterFactory.createXmlWriter(os);
          break;
        default:
          throw new IllegalStateException(
              String.format("No writer for %s file type", params.getOutputFileType().toString()));
      }
      processStream(br, writer);
      writer.close();
      log.info(String.format("Processing of file %s finished", params.getInputFilePath()));
    } catch (Exception e) {
      log.severe(e.getMessage());
    }
  }

  static void processStream(Reader br, FileWriter writer) throws Exception {
    StringBuilder sb = new StringBuilder();
    int r;
    while ((r = br.read()) != -1) {
      char c = (char) r;
      if (c == '.' || c == '?' || c == '!') {
        List<String> words = parseSentence(sb.toString());
        if (!words.isEmpty()) {
          Sentence sentence = new Sentence(words);
          // I have no idea why the sentence was supposed to be kept in the map key
          Map<Sentence, Void> sentenceMap = new HashMap<>();
          sentenceMap.put(new Sentence(words), null);
          writer.write(sentenceMap);
        }
        sb = new StringBuilder();
      }
      sb.append(c);
    }
  }

  static List<String> parseSentence(String stringSentence) {
    Pattern p = Pattern.compile("[\\w']+", Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = p.matcher(stringSentence);
    List<String> words =
        matcher
            .results()
            .filter(Objects::nonNull)
            .map(MatchResult::group)
            .map(String::trim)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toList());
    Collections.sort(words, String.CASE_INSENSITIVE_ORDER);
    return words;
  }
}
