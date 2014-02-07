package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.HashMap;
import java.util.Vector;

import ilm.framework.assignment.model.DomainObject;

public abstract class DataObject extends DomainObject {

    private String uniqueID;
    private String parentID;
    private String scopeID;

    public DataObject(String name, String description) {
        super(name, description);
    }

    /**
     * This method returns the uniqueID of a DomainObject.
     * 
     * @return uniqueID
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * This method sets the uniqueID of a DomainObject. This id must be set a single time during object creation.
     * 
     * @param id
     */
    public void setUniqueID(String id) {
        uniqueID = id;
    }

    /**
     * This method returns a String containing the XML notation for a Data Object.
     * 
     * @return
     */
    public abstract String toXML();

    /**
     * This method returns a String containing the Java notation for a Data Object.
     * 
     * @return
     */
    public abstract String toJavaString();

    /**
     * Returns the container of this element.
     */
    public String getParentID() {
        return parentID;
    }

    /**
     * Set this element container.
     * 
     * @param parent
     */
    public void setParentID(String fID) {
        parentID = fID;
    }

    /**
     * Get the scope of this object
     * 
     * @return
     */
    public String getScopeID() {
        return scopeID;
    }

    /**
     * Set the scope of this object
     * 
     * @return
     */
    public void setScopeID(String scopeID) {
        this.scopeID = scopeID;
    }

}
