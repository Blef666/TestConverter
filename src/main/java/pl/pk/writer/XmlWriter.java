package pl.pk.writer;

import lombok.extern.java.Log;
import pl.pk.model.Sentence;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.Writer;
import java.util.Map;

@Log
class XmlWriter implements FileWriter {

  private final XMLStreamWriter writer;

  public XmlWriter(Writer os) {

    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
    try {
      writer = xmlOutputFactory.createXMLStreamWriter(os);
      writer.writeStartDocument("utf-8", "1.0");
      writer.writeCharacters(System.getProperty("line.separator"));
      writer.writeStartElement("text");
      writer.writeCharacters(System.getProperty("line.separator"));
    } catch (XMLStreamException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void write(Map<Sentence, Void> sentence) {
    try {
      writer.writeStartElement("sentence");
      for (String word : sentence.keySet().iterator().next().getWords()) {
        writer.writeStartElement("word");
        writer.writeCharacters(word);
        writer.writeEndElement();
      }
      writer.writeEndElement();
      writer.writeCharacters(System.getProperty("line.separator"));
    } catch (XMLStreamException e) {
      log.info(
          String.format("There was a problem with writing sentence [%s]", sentence.toString()));
    }
  }

  @Override
  public void close() {
    try {
      writer.writeEndElement();
    } catch (XMLStreamException e) {
      log.info("There was a problem with ending <text> element");
    }
  }
}
