package pl.pk.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pk.commandline.CommandLineParameters;
import pl.pk.commandline.FileType;

import java.io.File;
import java.util.List;

public class FileProcessorTest {

  @Test
  public void shouldCreateXml() {
    CommandLineParameters clp =
        CommandLineParameters.builder()
            .inputFilePath(
                new File(getClass().getClassLoader().getResource("small.in").getFile())
                    .getAbsolutePath())
            .outputFilePath("temp.xml")
            .outputFileType(FileType.xml)
            .build();
    FileProcessor.process(clp);
    new File("temp.xml").delete();
  }

  @Test
  public void shouldCreateCsv() {
    CommandLineParameters clp =
        CommandLineParameters.builder()
            .inputFilePath(
                new File(getClass().getClassLoader().getResource("small.in").getFile())
                    .getAbsolutePath())
            .outputFilePath("temp.csv")
            .outputFileType(FileType.csv)
            .build();
    FileProcessor.process(clp);
    new File("temp.csv").delete();
  }

  @Test
  public void shouldParseUnicodeSentence() {
    List<String> words =
        FileProcessor.parseSentence("What\the  shouted was shocking:  停在那儿, 你这肮脏的掠夺者");
    Assertions.assertThat(words)
        .containsSequence("he", "shocking", "shouted", "was", "What", "你这肮脏的掠夺者", "停在那儿");
  }

  @Test
  public void shouldParseSpecialChars() {
    List<String> words = FileProcessor.parseSentence("#$%^&a:/b    )(*");
    Assertions.assertThat(words).containsSequence("a", "b");
  }
}
