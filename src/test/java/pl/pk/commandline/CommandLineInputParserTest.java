package pl.pk.commandline;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandLineInputParserTest {

  @Test
  public void shouldParseInput() {
    CommandLineParameters params =
        CommandLineInputParser.parse(new String[] {"-i", "input", "-o", "output", "-t", "csv"});
    Assertions.assertThat(params.getInputFilePath()).isEqualTo("input");
    Assertions.assertThat(params.getOutputFilePath()).isEqualTo("output");
    Assertions.assertThat(params.getOutputFileType()).isEqualTo(FileType.csv);
  }

  @Test()
  public void shouldThrowError_incorrectType() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            CommandLineInputParser.parse(
                new String[] {"-i", "input", "-o", "output", "-t", "cssv"}));
  }

  @Test()
  public void shouldThrowError_noInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> CommandLineInputParser.parse(new String[] {"-o", "output", "-t", "cssv"}));
  }

  @Test()
  public void shouldThrowError_noOutput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> CommandLineInputParser.parse(new String[] {"-i", "input", "-t", "cssv"}));
  }

  @Test()
  public void shouldThrowError_noType() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            CommandLineInputParser.parse(
                new String[] {
                  "-i", "input", "-o", "output",
                }));
  }
}
