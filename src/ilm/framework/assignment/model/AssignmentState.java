package ilm.framework.assignment.model;

import java.io.Serializable;
import java.util.Vector;
import java.util.Observable;

import usp.ime.line.ivprog.model.IVPProgramData;

public final class AssignmentState extends Observable {
    
    private Vector _objectList;
    private IVPProgramData data;
    
    public AssignmentState() {
        _objectList = new Vector();
        setData(new IVPProgramData());
    }
    
    public final void add(DomainObject object) {
        _objectList.add(object);
        setChanged();
        notifyObservers();
    }
    
    public final boolean remove(DomainObject object) {
        boolean isRemoved = _objectList.remove(object);
        setChanged();
        notifyObservers();
        return isRemoved;
    }
    
    public final DomainObject remove(int index) {
        DomainObject removedObject = (DomainObject) _objectList.remove(index);
        setChanged();
        notifyObservers();
        return removedObject;
    }
    
    public final DomainObject get(int index) {
        return (DomainObject) _objectList.get(index);
    }
    
    public final DomainObject getFromName(String name) {
        for (int i = 0; i < _objectList.size(); i++) {
            DomainObject obj = (DomainObject) _objectList.get(i);
            if (obj.getName().equals(name)) {
                return obj;
            }
        }
        return null;
    }
    
    public final DomainObject getFromDescription(String description) {
        for (int i = 0; i < _objectList.size(); i++) {
            DomainObject obj = (DomainObject) _objectList.get(i);
            if (obj.getDescription().equals(description)) {
                return obj;
            }
        }
        return null;
    }
    
    public final Vector getList() {
        return _objectList;
    }
    
    public final void setList(Vector list) {
        _objectList = list;
    }
    
    public final boolean equals(AssignmentState state) {
        if (_objectList.size() != state.getList().size()) {
            return false;
        }
        for (int i = 0; i < _objectList.size(); i++) {
            if (!_objectList.get(i).equals(state.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    public void updateState(DomainObject o) {
        setChanged();
        notifyObservers(o);
    }

    public IVPProgramData getData() {
        return data;
    }

    public void setData(IVPProgramData data) {
        this.data = data;
    }
}
