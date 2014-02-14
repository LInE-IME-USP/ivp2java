package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Assignment implements Serializable {
    private String          _proposition;
    private String          _name;
    private AssignmentState _initialState;
    private AssignmentState _currentState;
    private AssignmentState _expectedAnswer;
    private HashMap         _config;
    private HashMap         _metadata;
    
    public Assignment(String proposition, AssignmentState initial, AssignmentState current, AssignmentState expected) {
        _proposition = proposition;
        _name = "";
        _initialState = initial;
        _currentState = current;
        _expectedAnswer = expected;
        _config = new HashMap();
        _metadata = new HashMap();
    }
    
    public String getProposition() {
        return _proposition;
    }
    
    public String getName() {
        return _name;
    }
    
    public void setName(String name) {
        _name = name;
    }
    
    public AssignmentState getInitialState() {
        return _initialState;
    }
    
    public AssignmentState getCurrentState() {
        return _currentState;
    }
    
    public AssignmentState getExpectedAnswer() {
        return _expectedAnswer;
    }
    
    public void setConfigParameter(String key, String value) {
        _config.put(key, value);
    }
    
    public String getConfigParameter(String key) {
        return (String) _config.get(key);
    }
    
    public void setConfig(HashMap config) {
        _config = config;
    }
    
    public HashMap getConfig() {
        return _config;
    }
    
    public void setMetadataParameter(String key, String value) {
        _metadata.put(key, value);
    }
    
    public String getMetadataParameter(String key) {
        return (String) _metadata.get(key);
    }
    
    public void setMetadata(HashMap metadata) {
        _metadata = metadata;
    }
    
    public HashMap getMetadata() {
        return _metadata;
    }
    
    public void print() {
        for (int i = 0; i < _initialState.getList().size(); i++) {
            DomainObject obj = (DomainObject) _initialState.getList().get(i);
        }
        for (int i = 0; i < _currentState.getList().size(); i++) {
            DomainObject obj = (DomainObject) _currentState.getList().get(i);
        }
        if (_expectedAnswer != null) {
            for (int i = 0; i < _expectedAnswer.getList().size(); i++) {
                DomainObject obj = (DomainObject) _expectedAnswer.getList().get(i);
            }
        }
        Iterator keySetIterator = _config.keySet().iterator();
        while (keySetIterator.hasNext()) {
            String key = (String) keySetIterator.next();
        }
        Iterator metaDataIterator = _metadata.keySet().iterator();
        while (metaDataIterator.hasNext()) {
            String key = (String) metaDataIterator.next();
        }
    }
}
