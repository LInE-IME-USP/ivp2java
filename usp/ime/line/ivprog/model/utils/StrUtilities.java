/*
 * iVProg2 - interactive Visual Programming in the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * @description Static methods related to Strings
 * 
 * @author LOB
 * 
 */

package usp.ime.line.ivprog.model.utils;

public class StrUtilities {

  public static String readFromURL (java.applet.Applet applet, String strURL) {
    // Permite receber URL
    java.io.InputStream inputStream = null;
    java.net.URL endURL = null;
    // String strer = "";
    java.lang.StringBuffer stringbuffer = null;
    try { //
      endURL = new java.net.URL(strURL); // se for URL
    } catch (java.net.MalformedURLException e) {
      try { // se falhou, tente completar com endereço base do applet e completar com nome de arquivo
        endURL = new java.net.URL(applet.getCodeBase().toString() + "/" + strURL); // it it is URL
      } catch (java.net.MalformedURLException ue) {
        System.err.println("Error: leitura URL: applet=" + applet + ", " + strURL + ": not an URL: " + ue);
        return "";
        }
      }
    try {
      inputStream = endURL.openStream();
      return inputStream2String(inputStream);
    } catch (java.io.IOException ioe) {
      System.err.println("Error: reading from URL: " + strURL + ": " + ioe.toString());
      }
    return "";
    }

  public static String inputStream2String (java.io.InputStream inputStream) {
    try {
      java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputStream);
      java.io.StringWriter stringwriter = new java.io.StringWriter();
      int i = 8192;
      char[] cs = new char[i];
      try {
        for (;;) {
          int i_11_ = inputstreamreader.read(cs, 0, i);
          if (i_11_ == -1)
            break;
          stringwriter.write(cs, 0, i_11_);
          }
        stringwriter.close();
        inputStream.close();
      } catch (java.lang.Exception exception) {
        System.err.println("Error: reading from: " + inputStream + ": " + exception);
        // Trace.error(34,
	// ioexception.getMessage());
        return null;
        }
      inputStream.close();
      return stringwriter.toString();
    } catch (java.lang.Exception e2) { // java.io.IOException ioe)
      System.err.println("Error: reading from: " + inputStream + ": " + e2.toString());
      try {
        inputStream.close();
      } catch (java.io.IOException ioe) {
        System.err.println("Error: reading from inputStream=" + inputStream + ": " + ioe.toString());
        }
      return null;
      }
    }

  }
