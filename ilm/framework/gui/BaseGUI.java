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

package ilm.framework.gui;

import ilm.framework.SystemFactory;
import ilm.framework.assignment.IAssignment;
import ilm.framework.config.SystemConfig;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.awt.Cursor;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.view.utils.IconButtonUI;

public abstract class BaseGUI extends JPanel implements Observer {

  // Crated by the 'ilm.framework.gui.IlmBaseGUI' constructor
  public BaseGUI () {
    //D try { String str=""; System.err.println(str.charAt(3));   } catch (Exception e1) { e1.printStackTrace();   }
    }

  private static final long serialVersionUID = 1L;
  protected SystemConfig _config;
  protected SystemFactory _factory;
  protected Vector _domainGUIList;
  protected Vector _authoringGUIList;
  protected IAssignment _assignments;
  protected int _activeAssignment;

  public void setComponents (SystemConfig config, IAssignment commands, SystemFactory factory) {
    _config = config;
    _config.addObserver(this);
    _factory = factory;
    _domainGUIList = new Vector();
    _authoringGUIList = new Vector();
    _assignments = commands;
    _activeAssignment = 0;
    }

  public void initGUI (boolean isApplet) {
    initAssignments();
    initToolbar(_assignments.getIlmModuleList().values(), isApplet);
    }

  protected abstract void initAssignments();

  protected abstract void initToolbar(Collection moduleList, boolean isApplet);

  public void startDesktop () {
    final JFrame frame = new JFrame();
    frame.getContentPane().add(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }

  protected void updateAssignmentIndex (int index) {
    _activeAssignment = index;
    Iterator moduleIterator = _assignments.getIlmModuleList().values().iterator();
    while (moduleIterator.hasNext()) {
      IlmModule module = (IlmModule) moduleIterator.next();
      module.setAssignmentIndex(index);
      if (module instanceof AssignmentModule) {
        AssignmentModule m = (AssignmentModule) module;
        if (m.getObserverType() != AssignmentModule.ACTION_OBSERVER) {
          m.update(_assignments.getCurrentState(index), null);
          }
        }
      }
    Services.setCurrentState(_assignments.getCurrentState(index));
    }

  protected abstract void setAuthoringButton();

  protected abstract void setNewAssignmentButton();

  protected abstract void setCloseAssignmentButton();

  protected abstract void setOpenAssignmentButton();

  protected abstract void setSaveAssignmentButton();

  protected abstract void startAuthoring();

  protected abstract void addNewAssignment();

  protected abstract void closeAssignment(int index);

  protected abstract void openAssignmentFile(String fileName);

  protected abstract void saveAssignmentFile(String fileName);

  protected JButton makeButton (String imageName, String actionCommand, String toolTipText, String altText) {
    JButton button = new JButton();
    button.setActionCommand(actionCommand);
    button.setToolTipText(toolTipText);
    try {
      button.setIcon(new ImageIcon(BaseGUI.class.getResource("/usp/ime/line/resources/" + imageName + ".png"), altText));
      } catch (Exception e) {
      System.err.println("Error: image './usp/ime/line/resources/" + imageName + ".png' is missing: ilm/framework/gui/BaseGUI.java");
      }
    button.setUI(new IconButtonUI());
    return button;
    }

  }
