package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class IVPVectorReference extends Reference {
    private String             referencedVectorID = null;
    private String             positionExpID      = null;
    public static final String STRING_CLASS       = "vectorreference";
    
    public IVPVectorReference(String name, String description) {
        super(name, description);
    }
    
    /**
     * Return the referenced vector.
     * 
     * @return the referencedVector
     */
    public String getReferencedVector() {
        return referencedVectorID;
    }
    
    /**
     * Set the referenced vector.
     * 
     * @param referencedVec
     *            the referencedVector to set
     */
    public void setReferencedVector(String referencedVecID) {
        referencedVectorID = referencedVecID;
        IVPVector v = (IVPVector) Services.getService().getModelMapping().get(referencedVecID);
        setReferencedName(v.getCollectionName());
    }
    
    /**
     * Return the expression that specifies the positions of this reference.
     * 
     * @return the position
     */
    public String getPosition() {
        return positionExpID;
    }
    
    /**
     * Set the expression that specifies the positions of this reference.
     * 
     * @param position
     *            the position to set
     */
    public void setPosition(String pos) {
        positionExpID = pos;
    }
    
    public String toXML() {
        Expression posExp = (Expression) Services.getService().getModelMapping().get(positionExpID);
        String str = "<dataobject class=\"vectorreference\">" + "<id>" + getUniqueID() + "</id>" + "<referencedname>" + referencedName + "<referencedname>" + "<referencedtype>" + referencedType
                + "</referencedtype>" + "<index>" + posExp.toXML() + "</index>" + "</dataobject>";
        return str;
    }
    
    public String toJavaString() {
        return null;
    }
    
    @Override
    public boolean equals(DomainObject o) {
        // TODO Auto-generated method stub
        return false;
    }
}
