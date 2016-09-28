
/*
 * iLM - interactive Learning Module in the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework.modules;

import java.awt.Component;
import java.util.Observable;

public abstract class IlmModule extends Observable {

  protected String _name;
  protected int _assignmentIndex;
  protected IlmModuleToolbar _gui;

  public String getName () {
    return _name;
    }

  public Component getGUI () {
    addObserver(_gui); // 
    return _gui;
    }

  public void setAssignmentIndex (int index) {
    _assignmentIndex = index;
    setChanged();
    notifyObservers();
    }

  public abstract void print ();

  }
