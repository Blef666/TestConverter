package pl.pk.commandline;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandLineParameters {
  private String inputFilePath;
  private String outputFilePath;
  private FileType outputFileType;
}
