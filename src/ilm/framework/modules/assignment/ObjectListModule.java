package ilm.framework.modules.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collection;
import java.util.Observable;

public class ObjectListModule extends AssignmentModule implements Serializable {
    private Vector _objectList;
    
    public ObjectListModule() {
        _objectList = new Vector();
        _name = IlmProtocol.OBJECT_LIST_MODULE_NAME;
        _gui = new ObjectListModuleToolbar();
        _assignmentIndex = 0;
        _observerType = OBJECT_OBSERVER;
    }
    
    public Vector getObjectList() {
        return (Vector) _objectList.get(_assignmentIndex);
    }
    
    public void update(Observable o, Object arg) {
        if (o instanceof AssignmentState) {
            AssignmentState state = (AssignmentState) o;
            // TODO need a better non-brute force way to do this
            ((Vector) _objectList.get(_assignmentIndex)).clear();
            for (int i = 0; i < state.getList().size(); i++) {
                DomainObject obj = (DomainObject) state.getList().get(i);
                ((Vector) _objectList.get(_assignmentIndex)).add(obj);
            }
            setChanged();
            notifyObservers();
        }
    }
    
    public void setContentFromString(DomainConverter converter, int index, String moduleContent) {
        if (_objectList.size() == index) {
            addAssignment();
        }
        Vector array = converter.convertStringToObject(moduleContent);
        for (int i = 0; i < array.size(); i++) {
            DomainObject obj = (DomainObject) array.get(i);
            ((Vector) _objectList.get(index)).add(obj);
        }
    }
    
    public void addAssignment() {
        _objectList.add(new Vector());
    }
    
    public void print() {
        for (int i = 0; i < _objectList.size(); i++) {
            Vector list = (Vector) _objectList.get(i);
            for (int j = 0; j < list.size(); j++) {
                DomainObject obj = (DomainObject) list.get(j);
            }
        }
    }
    
    public String getStringContent(DomainConverter converter, int index) {
        if (((Vector) _objectList.get(_assignmentIndex)).size() == 0) {
            return "<" + _name + "/>";
        }
        String string = "<" + _name + "><objects>";
        string += converter.convertObjectToString(((Vector) _objectList.get(index)));
        string += "</objects></" + _name + ">";
        return string;
    }
    
    public void removeAssignment(int index) {
        _objectList.remove(index);
    }
    
    public void setDomainModel(DomainModel model) {
    }
    
    public void setState(AssignmentState state) {
    }
    
    public void setActionObservers(Collection values) {
    }
}
