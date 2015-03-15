package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] array = new String[2];
    String firstLine = "";
    String secondLine = "";
    boolean firstLineComplete = false;
    boolean stop = false;
    boolean newLineAtEnd = true;
    int lastPos = 0;

    for (int i = 0; i < lines.length() && !stop; i++) {
      char c = lines.charAt(i);

      switch (c) {
        case '\r':
          if (i != lines.length() - 1 && lines.charAt(i+1) != '\r' && lines.charAt(i+1) != '\n') {
            firstLine += c;
            firstLineComplete = true;
          } else {
            firstLine += c;
          }
          break;

        case '\n':
          if (i != lines.length() - 1 && lines.charAt(i+1) != '\r' && lines.charAt(i+1) != '\n') {
            firstLine += c;
            firstLineComplete = true;
          } else {
            firstLine += c;
          }
          break;

        default:
          firstLine += c;

          if (i == lines.length() - 1) {
            newLineAtEnd = false;
          }

          break;
      }

      if (firstLineComplete) {
        lastPos = i;
        stop = true;
      }
    }

    secondLine = lines.substring(lastPos+1);

    if (newLineAtEnd) {
      if (!firstLineComplete) {
        array[0] = firstLine;
        array[1] = "";
      } else {
        array[0] = firstLine;
        array[1] = secondLine;
      }
    } else {
      array[0] = "";
      array[1] = lines;
    }

    return array;
  }
}
