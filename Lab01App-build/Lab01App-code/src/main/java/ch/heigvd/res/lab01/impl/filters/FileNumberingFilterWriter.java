package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private int lineNumber = 1;
  private boolean firstCall = true;
  private char lastChar;

  private String filter(char[] str) {
    String newStr = "";

    if (firstCall) {
      newStr += lineNumber++;
      newStr += '\t';
      firstCall = false;
    }

    for (char c : str) {
      switch (c) {
        case '\n':
          if (lastChar == '\r') {
            newStr += lastChar;
          }

          newStr += c;
          newStr += lineNumber++;
          newStr += '\t';
          break;

        case '\r':
          break;

        default:
          if (lastChar == '\r') {
            newStr += lastChar;
            newStr += lineNumber++;
            newStr += '\t';
          }

          newStr += c;
          break;
      }

      lastChar = c;
    }

    return newStr;
  }

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String toWrite = str.substring(off, off + len);
    toWrite = filter(toWrite.toCharArray());
    out.write(toWrite);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    write(Character.toString((char) c), 0, 1);
  }
}
