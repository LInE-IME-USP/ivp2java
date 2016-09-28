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

/*
 * 2.1.0 : 2016/09/27 : improvement on locker (now if the block is crated from file, ir rised closed); new icons to create "var", "param" and "command"
 * 2.0.2 : 2016/09/22 : now is possible to open a file with command line ('java -jar iVProg2 file.ivp2')
 * 2.0.1 : 2016/09/18 : improvement on locker (now if the block is crated from file, ir rised closed)
 * 0.1.1 : 2016/09/18 : changed default locker (now when new block is created it rised open) - bad number... iVProg2 must start on version 2
 * 
 */

package usp.ime.line.ivprog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.ZipFile;

import ilm.framework.IlmProtocol;
import ilm.framework.SystemControl;
import ilm.framework.gui.IlmBaseGUI;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.xml.bind.DatatypeConverter; //TODO remover - com implica Java 1.6 ou maior!
//TODO: 
// src/usp/ime/line/ivprog/Ilm.java:32:
//  import javax.xml.bind.DatatypeConverter;
// src/ilm/framework/comm/IlmDesktopFileRW.java:22:
//  import javax.xml.bind.DatatypeConverter;
// src/usp/ime/line/ivprog/Ilm.java:100:
//  DatatypeConverter
// src/ilm/framework/comm/IlmDesktopFileRW.java:35:
//  DatatypeConverter


import usp.ime.line.ivprog.model.utils.StrUtilities;
import usp.ime.line.ivprog.model.utils.Tracking;

public class Ilm extends JApplet implements IlmProtocol {

  public static final String IVPVERSION = "2.1.0"; // 2016/09/27

  public static final boolean DEBUG = true; // to print messages during execution to debug

  private static boolean staticIsApplet = false;
  public static boolean isApplet () { return staticIsApplet; }

  private static final long serialVersionUID = 1L;
  private static IlmProtocol _ilmProtocol;
  private static Ilm instance;
  private String MA_PARAM_PropositionUrl = "";
  private String MA_PARAM_Proposition = "";
  public static String ENCODE_TYPE = "UTF-8";

  // Constructor used only to create Ilm instance for application
  public Ilm () {
    this.instance = this;
    }


  // Appliction starting point
  public static void main (String [] args) {
    final SystemControl ilmControl = new SystemControl();
    System.out.println("\n .: iVProg - www.matematica.br / line.ime.usp.br :.\n    Version: " + IVPVERSION + "\n");

    Ilm ilm = new Ilm();

    staticIsApplet = false;

    ilmControl.initialize(false, args, new IlmSystemFactory()); // ilm.framework.SystemControl.initialize(isApplet,parameterList[],factory)
    _ilmProtocol = ilmControl.getProtocol();
    try {
//TODO 2016/09/25 precisa do runnable??? - removi
//      SwingUtilities.invokeLater(new Runnable () {
//        public void run () {
          ilmControl.startDesktopGUI();
//          }
//        });
    } catch (Exception e) {
      System.err.println("createGUI didn't complete successfully");
      }

    // ./src/ilm/framework/config/SystemConfig.java: getParameters(key)
    String strFile = ilmControl.getSystemConfig().getParameter("file");
    //D System.out.println("-------------------\nusp/ime/line/ivprog/Ilm.java: file=" + strFile);

    // If is any valid file under command line, open it!
    // IlmBaseGUI extends BaseGUI
    if (strFile!=null && strFile!="") {
      IlmBaseGUI gui = (IlmBaseGUI) ilmControl.getApplicationGUI();
      gui.openAssignmentFileCommandLine(strFile);
      }

    } // public static void main(String [] args)


  // Applet starting point
  public void init () {
    String [] args = GetParam();
    final SystemControl ilmControl = new SystemControl();
    System.out.println(" .: iVProg - www.matematica.br / line.ime.usp.br :.\n   Version: " + IVPVERSION + "\n");
    ilmControl.initialize(true, args, new IlmSystemFactory());

    staticIsApplet = true;

    _ilmProtocol = ilmControl.getProtocol();
    IlmBaseGUI gui = (IlmBaseGUI) ilmControl.getAppletGUI();

    add(gui);

    String paramPropositionURL = "", // it is a string content of IVP or it is an URL of the content file
    paramPropositionIsURL = ""; // if "true" => 'paramPropositionSTR' it is an URL with file address

    paramPropositionURL = getParameter("iLM_PARAM_Assignment"); // iLM 2.0
    if (paramPropositionURL==null || paramPropositionURL.length()<2) { // iLM 1.0
      paramPropositionURL = getParameter("MA_PARAM_Proposition");
      paramPropositionIsURL = getParameter("MA_PARAM_PropositionURL");
      System.out.println("./usp/ime/line/ivprog/Ilm.java: init(): iLM 1.0 with MA_PARAM_PropositionURL: " + paramPropositionIsURL);
      }
    else
      System.out.println("./usp/ime/line/ivprog/Ilm.java: init(): iLM 2.0 with " + paramPropositionURL);

    Tracking.setBase(getParameter("MA_PARAM_addresPOST"));
    
    // Get the content from URL file
    String fileAsString = StrUtilities.readFromURL(this, paramPropositionURL);
    byte [] decoded = DatatypeConverter.parseBase64Binary(fileAsString);
    fileAsString = new String(decoded);

    // Using the iVProg2 file content, load it
    if (fileAsString != null && !"".equals(fileAsString)) {
      (gui).openAssignmentFromURL(fileAsString);
      }
    instance = this;
    }

  private String [] GetParam () {
    String [] s = new String[1];
    s[0] = "";
    return s;
    }

  public float getEvaluation () {
    float resposta = _ilmProtocol.getEvaluation();
    System.out.println("./usp/ime/line/ivprog/Ilm.java: getEvaluation():  " + resposta);
    return resposta;
    }

  public String getAnswer () {
    String str = _ilmProtocol.getAnswer(); // ilm.framework.IlmProtocol
    byte [] message = str.getBytes();
    String encoded = DatatypeConverter.printBase64Binary(message);
    System.out.println("./usp/ime/line/ivprog/Ilm.java: getAnswer():  " + str);
    return encoded;
    }

  public ZipFile getAssignmentPackage () {
    return _ilmProtocol.getAssignmentPackage();
    }

  public static Ilm getInstance () {
    return instance;
    }

  }
