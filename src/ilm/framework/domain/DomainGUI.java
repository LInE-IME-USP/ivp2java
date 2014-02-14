package ilm.framework.domain;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

import java.util.Vector;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observer;

import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;

public abstract class DomainGUI extends JPanel implements Observer {
    /**
     * @attribute serial version due to javax.swing specification
     * @attribute the proposition of the assignment shown by this user interface
     * @attribute the state of the assignment shown by this user interface
     * @attribute the list of possible domain actions the user can do
     */
    private static final long serialVersionUID = 1L;
    protected String          _proposition;
    protected AssignmentState _state;
    
    /**
     * Defines the assignment-specific data that this user interface must keep. Also updates the communication among the framework components.
     * 
     * @param proposition
     * @param curState
     * @param moduleList
     */
    public void setAssignment(String proposition, AssignmentState curState, Collection moduleList) {
        _proposition = proposition;
        _state = curState;
        _state.addObserver(this);
        HashMap _actionList = Services.getService().getController().getActionList();
        Iterator actionListIterator = _actionList.values().iterator();
        while (actionListIterator.hasNext()) {
            DomainAction action = (DomainAction) actionListIterator.next();
            action.setState(_state);
            action.deleteObservers();
            Iterator moduleIterator = moduleList.iterator();
            while (moduleIterator.hasNext()) {
                IlmModule module = (IlmModule) moduleIterator.next();
                if (module instanceof AssignmentModule) {
                    AssignmentModule assMod = (AssignmentModule) module;
                    if (assMod.getObserverType() != AssignmentModule.OBJECT_OBSERVER) {
                        action.addObserver(assMod);
                    }
                }
            }
        }
        initDomainGUI();
    }
    
    /**
     * Initialization method to the user interface features (widgets) that depend on the assignment to be defined. It is called at the method "setAssignment", just after the assignment is defined.
     * 
     * @see example.ilm.gui.IlmDomainGUI
     */
    protected abstract void initDomainGUI();
    
    /**
     * @return a list of domain objects selected by the user. It is used by authoringGUI to author a new assignment.
     * 
     * @see example.ilm.gui.IlmDomainGUI
     */
    public abstract Vector getSelectedObjects();
    
    /**
     * @return the current state of the assignment shown by this user interface. It is not a final method, so it can be overriden by the child class to add some behavior.
     */
    public AssignmentState getCurrentState() {
        return _state;
    }
}
