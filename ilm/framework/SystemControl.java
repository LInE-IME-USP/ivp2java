/*
 * iLM - interactive Learning Modules in the Internet
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework;

import java.io.File;
import java.util.Map;

import ilm.framework.assignment.AssignmentControl;
import ilm.framework.comm.CommControl;
import ilm.framework.config.*;
import ilm.framework.gui.BaseGUI;

public final class SystemControl {

  private SystemConfig _config;
  private AssignmentControl _assignmentControl;
  private CommControl _comm;
  private BaseGUI _gui;
  private boolean isApplet = false;

  // Called by: aplication
  // Example in iVProg: ./usp/ime/line/ivprog/Ilm.java to get access to parameter 'file'
  public SystemConfig getSystemConfig () {
    return _config;
    }

  private static void listParameters (String [] parameterList) {
    int sizeList = 0;
    if (parameterList!=null)
      sizeList = parameterList.length;
    System.out.println("./ilm/framework/SystemControl.java: listParameters = ");
    for (int i=0; i<sizeList; i++)
      System.out.println(" " + i + ": " + parameterList[i]);
    }

  private static boolean isFile (String filePathString) {
    File file = new File(filePathString);
    if (file.exists() && !file.isDirectory()) { // exist and it is not a directory
      return true;
      }
    return false;
    }

  private static String isFileParameter (boolean isApplet, String [] parameterList) {
    int sizeList = 0;
    if (isApplet)
      return null; // avoid problem...
    if (parameterList!=null)
      sizeList = parameterList.length;
    //D
System.out.println("./ilm/framework/SystemControl.java: isFileParameter(...): ");
    for (int i=0; i<sizeList; i++) {
      if (isFile(parameterList[i]))
        return parameterList[i];
      }
    return null;
    }

  // Called by: application starting point
  // Example in iVProg: 'usp.ime.line.ivprog.Ilm.main(...)' or 'usp.ime.line.ivprog.Ilm.init(...)'
  public void initialize (boolean isApplet, String [] parameterList, SystemFactory factory) {
    IParameterListParser parser; // ilm/framework/config/IParameterListParser.java: interface
    this.isApplet = isApplet;

    //D try { String str=""; System.err.println(str.charAt(3)); } catch (Exception e1) { e1.printStackTrace(); }
    //D listParameters(parameterList);

    String strFile = null;
    if (isApplet) {
      parser = new AppletParameterListParser();
      }
    else {
      parser = new ParameterListParser();
      strFile = isFileParameter(isApplet, parameterList);
      }

    Map parsedParameterList = parser.Parse(parameterList);

    if (strFile!=null) // is file with this name, add it as Property
      parsedParameterList.put("file", strFile);

    _config = new SystemConfig(isApplet, parsedParameterList);
    if (!isApplet) // if applet 'debug' can rise error
      if (ilm.framework.IlmProtocol.DEBUG) try {
	System.out.println("./ilm/framework/SystemControl.java: initialize: file=" + parsedParameterList.get("file"));
        _config.getParameters().list(System.out); // print the Properties
      } catch (Exception e) { System.err.println("./ilm/framework/SystemControl.java: initialize: error in debug print properties!"); }

    initComponents(factory);
    }

  private void initComponents (SystemFactory factory) {
    _comm = factory.createCommControl(_config);
    _assignmentControl = factory.createAssignmentControl(_config, _comm, factory.getDomainModel(_config), factory.getDomainConverter());
    _gui = factory.createBaseGUI(_config, _assignmentControl, factory);
    }

  public IlmProtocol getProtocol () {
    return _assignmentControl;
    }

  public CommControl getCommunicationControl () {
    return _comm;
    }

  public void startDesktopGUI () {
    _gui.initGUI(false);
    _gui.startDesktop();
    }

  public BaseGUI getApplicationGUI () {
    //_gui.initGUI(true);
    return _gui; // ilm.framework.gui.BaseGUI _gui
    }

  public BaseGUI getAppletGUI () {
    _gui.initGUI(true);
    return _gui; // ilm.framework.gui.BaseGUI _gui
    }

  }
