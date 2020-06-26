package pl.pk.commandline;

import lombok.extern.java.Log;
import org.apache.commons.cli.*;

@Log
public class CommandLineInputParser {
  public static CommandLineParameters parse(String[] args) {
    Options options = new Options();
    options.addOption(Option.builder("h").longOpt("help").desc("show help").build());
    options.addOption(
        Option.builder("i").longOpt("input").hasArg().required().desc("path to input file").build());
    options.addOption(
        Option.builder("o").longOpt("output").hasArg().required().desc("path to output file").build());
    options.addOption(
        Option.builder("t")
            .longOpt("type")
            .hasArg()
            .required()
            .desc("output file type [csv, xml]")
            .build());

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd;
    try {
      cmd = parser.parse(options, args);

    } catch (ParseException e) {
      final HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("ConsoleReader", options);
      throw new IllegalArgumentException("Parsing failed.  Reason: " + e.getMessage());
    }
    if (cmd.hasOption("help")) {
      final HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("ConsoleReader", options);
    }
    return CommandLineParameters.builder()
        .inputFilePath(cmd.getOptionValue("input"))
        .outputFilePath(cmd.getOptionValue("output"))
        .outputFileType(FileType.map(cmd.getOptionValue("type")))
        .build();
  }
}
