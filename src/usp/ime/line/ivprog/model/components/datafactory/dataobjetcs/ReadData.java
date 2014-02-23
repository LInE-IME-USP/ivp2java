package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class ReadData extends CodeComponent {
    private String             writableObject = null;
    public static final String STRING_CLASS   = "read";
    
    public ReadData(String name, String description) {
        super(name, description);
    }
    
    /**
     * Return the printable object.
     * 
     * @return the printableObject
     */
    public String getWritableObject() {
        return writableObject;
    }
    
    /**
     * Set the printable object.
     * 
     * @param printableObject
     *            the printableObject to set
     */
    public void setWritableObject(String printableObject) {
        this.writableObject = printableObject;
    }
    
    /**
     * Removes the printable object and return it.
     */
    public String removeWritableObject() {
        writableObject = null;
        return writableObject;
    }
    
    public String toXML() {
        Expression printable = (Expression) Services.getService().getModelMapping().get(writableObject);
        String str = "<dataobject class=\"read\"><id>" + getUniqueID() + "</id>" + "<writable>" + printable.toXML() + "</writable></dataobject>";
        return str;
    }
    
    public String toJavaString() {
        String str = "";
        VariableReference varRef = (VariableReference) Services.getService().getModelMapping().get(writableObject);
        str = getStrForType(varRef.getReferencedType(), varRef);
        return str;
    }
    
    private String getStrForType(short type, VariableReference varRef) {
        String str = "";
        if (type == Expression.EXPRESSION_INTEGER) {
            str += "readInteger.showAskUser();" + varRef.toJavaString() + " = readInteger.getFinalValue(); if(readInteger.isInterrupt()) return;";
        } else if (type == Expression.EXPRESSION_DOUBLE) {
            str += "readDouble.showAskUser();" + varRef.toJavaString() + " = readDouble.getFinalValue(); if(readDouble.isInterrupt()) return;";
        } else if (type == Expression.EXPRESSION_BOOLEAN) {
            str += "readBoolean.showAskUser();" + varRef.toJavaString() + " = readBoolean.getFinalValue(); if(readDouble.isInterrupt()) return;";
        } else if (type == Expression.EXPRESSION_STRING) {
            str += "readString.showAskUser();" + varRef.toJavaString() + " = readString.getFinalValue(); if(readDouble.isInterrupt()) return;";
        }
        return str;
    }
    
    public boolean equals(DomainObject o) {
        return false;
    }
    
    public void updateParent(String lastExp, String newExp, String operationContext) {
        if (writableObject == lastExp)
            writableObject = newExp;
    }
}