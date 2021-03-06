/*
 * iLM - interactive Learning Modules to the Internet
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework.assignment.model;

import ilm.framework.domain.DomainModel;

import java.io.Serializable;
import java.util.Vector;
import java.util.Observable;

public abstract class DomainAction extends Observable implements Cloneable, Serializable {

  private String _name;
  protected String _description;
  protected Vector _parameterList;
  protected AssignmentState _currentState;
  private boolean _isUndo;
  private boolean _isRedo;
  protected boolean executingInSilence = false; //TODO 2016/09/25 eliminate in favor of 'staticExecutingInSilence' since ALL instance run under the same 'silence' (or not)

  // To be accessed by every DomainAction (like 'usp/ime/line/ivprog/model/domainaction/ChangeExpressionSign.java' in iVProg2)
  // Define by: ilm/framework/modules/assignment/HistoryModule.java : public void executeActions()
  // Used by: ...
  private static boolean staticExecutingInSilence = false;
  public static boolean getExecutingInSilence () { return staticExecutingInSilence; }
  public static void setExecutingInSilence (boolean bool) { staticExecutingInSilence = bool; } // ilm/framework/modules/assignment/HistoryModule.java

  public DomainAction (String name, String description) {
    _name = name;
    _description = description;
    _parameterList = new Vector();
    }

  /**
   * Defines the DomainModel on which this action is executed. The concrete
   * DomainAction classes must have an attribute that is from a class that
   * inherits from DomainModel, and this method set its value. It is called
   * internally from the framework.
   * 
   * @param an instance of a class that inherits from DomainModel
   * 
   * @see example.ilm.model.ActionAddSubString
   */
  public abstract void setDomainModel (DomainModel model);

  /**
   * Template Method that is called when this action is being executed. It
   * encapsulates Hook Method called "executeAction"
   */
  public final void execute () {
    if (ilm.framework.IlmProtocol.DEBUG) System.out.println("./ilm/framework/assignment/model/DomainAction.java: execute(): " + this._name + ": " + this._description);
    executingInSilence = false; // false => do not worry to execute in silence (from reading file)
    executeAction(); // application implements (e.g. in iVProg2: ./usp/ime/line/ivprog/model/domainaction/CreateExpression.java)
    _isUndo = false;
    setChanged();
    notifyObservers();
    _isRedo = false;
    }

  /**
   * Silence! Used during file content load (of iLM files)
   * Called by: ilm/framework/modules/assignment/HistoryModule.java: executeActions()
   */
  public final void executeInSilence () {
    if (ilm.framework.IlmProtocol.DEBUG) System.out.println("./ilm/framework/assignment/model/DomainAction.java: executeInSilence(): " + this._name + ": " + this._description);
    executingInSilence = true; // true => attention, execute in silence (it is reading file)
    executeAction(); // application implements (e.g. in iVProg2: ./usp/ime/line/ivprog/model/domainaction/CreateExpression.java)
    _isUndo = false;
    _isRedo = false;
    executingInSilence = false;
    }

  /**
   * Hook Method that must be implemented to call methods from DomainModel
   * when this action is being executed
   * 
   * @see example.ilm.model.ActionAddSubString
   */
  protected abstract void executeAction ();

  /**
   * Template Method that is called when this action is being undone. It
   * encapsulates Hook Method called "undoAction"
   */
  public final void undo () {
    if (ilm.framework.IlmProtocol.DEBUG) System.out.println("./ilm/framework/assignment/model/DomainAction.java: undo(): " + this._name + ": " + this._description);
    undoAction();
    _isUndo = true;
    setChanged();
    notifyObservers();
    }

  /**
   * Silence!!
   */
  public final void undoInSilence () {
    if (ilm.framework.IlmProtocol.DEBUG) System.out.println("./ilm/framework/assignment/model/DomainAction.java: undoInSilence(): " + this._name + ": " + this._description);
    undoAction();
    _isUndo = true;
    }

  /**
   * Hook Method that must be implemented to call methods from DomainModel
   * when this action is being undone
   * 
   * @see example.ilm.model.ActionAddSubString
   */
  protected abstract void undoAction ();

  /**
   * @return a boolean that indicates whether this action is a "undoing"
   *         action
   */
  public final boolean isUndo () {
    return _isUndo;
    }

  /**
   * Defines whether this action is a "redoing" action
   * 
   * @param the
   *            new state of this action regaring its "redoing" attribute
   */
  public final void setRedo (boolean b) {
    _isRedo = b;
    }

  /**
   * @return a boolean that indicates whether this action is a "redoing"
   *         action
   */
  public final boolean isRedo () {
    return _isRedo;
    }

  /**
   * Defines the list of DomainObject which are the parameters that will be
   * used for the execution of this action
   * 
   * @param a
   *            list of DomainObject
   */
  public final void setObjectParameter (Vector parameterList) {
    _parameterList = parameterList;
    }

  /**
   * Defines a new assignment state for this action
   * 
   * @param the
   *            new action's assignment state
   */
  public final void setState (AssignmentState currentState) {
    _currentState = currentState;
    }

  /**
   * @return the action's current assignment state
   */
  public final AssignmentState getState () {
    return _currentState;
    }

  /**
   * @return the action's name
   */
  public final String getName () {
    return _name;
    }

  /**
   * @return the action's description
   */
  public final String getDescription () {
    return _description;
    }

  /**
   * Defines a new description for this action
   * 
   * @param the
   *            new action's description
   */
  public final void setDescription (String string) {
    _description = string;
    }

  /**
   * @return a copy of the current DomainAction
   * 
   *         Usually used in AssignmentModules for storing lists of actions
   */
  public final Object clone () {
    try {
      return super.clone();
      } catch (CloneNotSupportedException e) {
      return null;
      }
    }

  /**
   * Compares this with another DomainAction
   * 
   * @param the
   *            other action to be compare to
   * @return the result of the comparison, true or false
   * 
   * @see example.ilm.model.ActionAddSubString for a simple example
   */
  public abstract boolean equals (DomainAction a);

  }
