package usp.ime.line.ivprog;

import java.util.Vector;

import usp.ime.line.ivprog.model.domainaction.CreateVariable;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;

public class IVPDomainConverter implements DomainConverter {
    
    public Vector convertStringToObject(String objectListDescription) {
        return null;
    }
    
    public String convertObjectToString(Vector objectList) {
        return "";
    }
    
    public Vector convertStringToAction(String actionListDescription) {
        return null;
    }
    
    public String convertActionToString(Vector actionList) {
        for (int i = 0; i < actionList.size(); i++) {
        }
        return null;
    }
}
