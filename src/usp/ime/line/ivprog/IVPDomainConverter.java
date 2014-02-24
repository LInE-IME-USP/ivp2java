package usp.ime.line.ivprog;

import java.util.Vector;

import ilm.framework.domain.DomainConverter;

public class IVPDomainConverter implements DomainConverter {
    public Vector convertStringToObject(String objectListDescription) {
        return null;
    }
    
    public String convertObjectToString(Vector objectList) {
        return null;
    }
    
    public Vector convertStringToAction(String actionListDescription) {
        return null;
    }
    
    public String convertActionToString(Vector actionList) {
        for (int i = 0; i < actionList.size(); i++) {
            System.out.println(actionList.get(i));
        }
        return null;
    }
}
