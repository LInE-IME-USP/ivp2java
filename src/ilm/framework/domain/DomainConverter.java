package ilm.framework.domain;

import java.io.OutputStream;
import java.util.Vector;

public interface DomainConverter {
    /**
     * Converts a string to a list of DomainObject
     * 
     * @param objectListDescription
     * @return a list of DomainObjects created using the description. Return an empty list if the string is empty or incompatible.
     * 
     * @see example.ilm.model.IlmDomainConverter for a simple example
     * 
     * @throws it
     *             is not yet implemented but this method may in the future throw exceptions due to parsing errors
     */
    public Vector convertStringToObject(String objectListDescription);
    
    /**
     * Converts a list of DomainObject to a string
     * 
     * @param objectList
     * @return a string containing the description of the list of DomainObjects.
     * 
     * @see example.ilm.model.IlmDomainConverter for a simple example
     */
    public String convertObjectToString(Vector objectList);
    
    /**
     * Converts a string to a list of DomainAction
     * 
     * @param actionListDescription
     * @return a list of DomainActions created using the description. Return an empty list if the string is empty or incompatible.
     * 
     * @see example.ilm.model.IlmDomainConverter for a simple example
     * 
     * @throws it
     *             is not yet implemented but this method may in the future throw exceptions due to parsing errors
     */
    public Vector convertStringToAction(String actionListDescription);
    
    /**
     * Converts a list of DomainAction to a string
     * 
     * @param actionList
     * @return a string containing the description of the list of DomainActions
     * 
     * @see example.ilm.model.IlmDomainConverter for a simple example
     */
    public String convertActionToString(Vector actionList);
}