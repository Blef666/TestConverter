package pl.pk.commandline;

public enum FileType {
  csv,
  xml;

  public static FileType map(String fileType) {
    try {
      return valueOf(fileType);
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format("File type [%s] not defined", fileType));
    }
  }
}
