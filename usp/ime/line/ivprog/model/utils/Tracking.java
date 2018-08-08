/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.model.utils;

public class Tracking {

  private static String baseURL;
  private static boolean isApplet = false;

  public static void setBase (String baseURL1) {
    baseURL = baseURL1 + "&track=1";
    isApplet = true;
    }

  // Called by: usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel
  public static void track (String trackingData) {
    if (usp.ime.line.ivprog.Ilm.DEBUG)
      System.err.println("Tracking.track: " + trackingData);
    //TODO Every action in Ilm (wrong!) is calling this (it must be in Ilm), perhaps we can use it to
    //TODO analyse the learner rationale. But not for now, ignore it.
    if (1==1) return;
    if (isApplet) {
      HttpUtil httpUtil = new HttpUtil();
      // variaveis post
      httpUtil.addPostVariable("trackingData", trackingData);
      try {
        httpUtil.makePostRequest(baseURL);
      } catch (Exception e) {
        System.err.println("usp/ime/line/ivprog/model/utils/Tracking.java: track(String trackingData): " + e.toString());
        e.printStackTrace();
        }
      }
    }

  public static boolean isApplet () {
    return isApplet;
    }

  public static void setApplet (boolean isApplet1) {
    isApplet = isApplet1;
    }

  }
