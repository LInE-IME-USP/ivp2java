package ilm.framework.modules.assignment;

import java.io.Serializable;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Stack;
import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;

public class UndoRedoModule extends AssignmentModule implements Serializable {
    private Stack _undoStack;
    private Stack _redoStack;
    
    public UndoRedoModule() {
        _undoStack = new Stack();
        _redoStack = new Stack();
        _name = IlmProtocol.UNDO_REDO_MODULE_NAME;
        _gui = new UndoRedoModuleToolbar();
        _observerType = ACTION_OBSERVER;
    }
    
    public void undo() {
        DomainAction action = (DomainAction) ((Stack) _undoStack.get(_assignmentIndex)).pop();
        ((Stack) _redoStack.get(_assignmentIndex)).push(action);
        action.undo();
    }
    
    public void redo() {
        DomainAction action = (DomainAction) ((Stack) _redoStack.get(_assignmentIndex)).pop();
        action.setRedo(true);
        // _undoStack.push(action); -> it is already done within
        // action.execute()
        action.execute();
    }
    
    public boolean isUndoStackEmpty() {
        return ((Stack) _undoStack.get(_assignmentIndex)).isEmpty();
    }
    
    public boolean isRedoStackEmpty() {
        return ((Stack) _redoStack.get(_assignmentIndex)).isEmpty();
    }
    
    public void update(Observable o, Object arg) {
        if (o instanceof DomainAction) {
            DomainAction action = (DomainAction) o;
            if (action.isUndo()) {
                setChanged();
                notifyObservers();
            } else {
                if (!action.isRedo()) {
                    ((Stack) _redoStack.get(_assignmentIndex)).clear();
                }
                ((Stack) _undoStack.get(_assignmentIndex)).push((DomainAction) action.clone());
                setChanged();
                notifyObservers();
            }
        }
    }
    
    public void setContentFromString(DomainConverter converter, int index, String moduleContent) {
        if (_undoStack.size() == index && _redoStack.size() == index) {
            addAssignment();
        }
        String undoCommands = getActions("undostack", moduleContent);
        String redoCommands = getActions("redostack", moduleContent);
        Vector actionArray = converter.convertStringToAction(undoCommands);
        for (int i = 0; i < actionArray.size(); i++) {
            ((Stack) _undoStack.get(index)).push(actionArray.get(i));
        }
        actionArray = converter.convertStringToAction(redoCommands);
        for (int i = 0; i < actionArray.size(); i++) {
            ((Stack) _redoStack.get(index)).push(actionArray.get(i));
        }
    }
    
    private String getActions(String string, String moduleContent) {
        int startIndex = moduleContent.indexOf("<" + string + ">") + 2 + string.length();
        int endIndex = moduleContent.indexOf("</" + string + ">");
        if (startIndex == -1 || endIndex == -1) {
            return "";
        }
        String s = moduleContent.substring(startIndex, endIndex);
        return s;
    }
    
    public void addAssignment() {
        _undoStack.add(new Stack());
        _redoStack.add(new Stack());
    }
    
    public void print() {
        for (int i = 0; i < _undoStack.size(); i++) {
            Stack stack = (Stack) _undoStack.get(i);
            for (int j = 0; j < stack.size(); j++) {
                DomainAction a = (DomainAction) stack.get(j);
            }
        }
    }
    
    public String getStringContent(DomainConverter converter, int index) {
        if (((Stack) _undoStack.get(index)).size() == 0 & ((Stack) _redoStack.get(index)).size() == 0) {
            return "<" + _name + "/>";
        }
        String string = "<" + _name + ">";
        if (((Stack) _undoStack.get(index)).size() > 0) {
            string += "<undostack>";
            string += converter.convertActionToString(((Vector) _undoStack.get(index)));
            string += "</undostack>";
        }
        if (((Stack) _redoStack.get(index)).size() > 0) {
            string += "<redostack>";
            converter.convertActionToString((Vector) _redoStack.get(index));
            string += "</redostack>";
        }
        string += "</" + _name + ">";
        return string;
    }
    
    public void removeAssignment(int index) {
        _undoStack.remove(index);
        _redoStack.remove(index);
    }
    
    public void setDomainModel(DomainModel model) {
        for (int i = 0; i < _undoStack.size(); i++) {
            Stack stack = (Stack) _undoStack.get(i);
            for (int j = 0; j < stack.size(); j++) {
                DomainAction action = (DomainAction) stack.get(j);
                action.setDomainModel(model);
            }
        }
        for (int i = 0; i < _redoStack.size(); i++) {
            Stack stack = (Stack) _redoStack.get(i);
            for (int j = 0; j < stack.size(); j++) {
                DomainAction action = (DomainAction) stack.get(j);
                action.setDomainModel(model);
            }
        }
    }
    
    public void setState(AssignmentState state) {
        for (int i = 0; i < ((Stack) _undoStack.get(_undoStack.size() - 1)).size(); i++) {
            DomainAction action = (DomainAction) ((Stack) _undoStack.get(_undoStack.size() - 1)).get(i);
            action.setState(state);
        }
        for (int i = 0; i < ((Stack) _redoStack.get(_redoStack.size() - 1)).size(); i++) {
            DomainAction action = (DomainAction) ((Stack) _redoStack.get(_redoStack.size() - 1)).get(i);
            action.setState(state);
        }
    }
    
    public void setActionObservers(Collection values) {
        Iterator valuesIterator = values.iterator();
        while (valuesIterator.hasNext()) {
            IlmModule m = (IlmModule) valuesIterator.next();
            if (m instanceof AssignmentModule) {
                for (int i = 0; i < _undoStack.size(); i++) {
                    Stack stack = (Stack) _undoStack.get(i);
                    for (int j = 0; j < stack.size(); j++) {
                        DomainAction action = (DomainAction) stack.get(j);
                        action.addObserver((AssignmentModule) m);
                    }
                }
                for (int i = 0; i < _redoStack.size(); i++) {
                    Stack stack = (Stack) _redoStack.get(i);
                    for (int j = 0; j < stack.size(); j++) {
                        DomainAction action = (DomainAction) stack.get(j);
                        action.addObserver((AssignmentModule) m);
                    }
                }
            }
        }
    }
}
