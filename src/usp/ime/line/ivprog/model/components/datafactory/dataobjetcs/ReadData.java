package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class ReadData extends CodeComponent {
    
    private String             writableObject = null;
    public static final String STRING_CLASS      = "read";
    
    public ReadData(String name, String description) {
        super(name, description);
    }
    
    /**
     * Return the printable object.
     * 
     * @return the printableObject
     */
    public String getPrintableObject() {
        return writableObject;
    }
    
    /**
     * Set the printable object.
     * 
     * @param printableObject
     *            the printableObject to set
     */
    public void setPrintableObject(String printableObject) {
        this.writableObject = printableObject;
    }
    
    /**
     * Removes the printable object and return it.
     */
    public String removePrintableObject() {
        writableObject = null;
        return writableObject;
    }
    
    public String toXML() {
        Expression printable = (Expression) Services.getService().getModelMapping().get(writableObject);
        String str = "<dataobject class=\"print\"><id>" + getUniqueID() + "</id>" + "<printable>" + printable.toXML() + "</printable></dataobject>";
        return str;
    }
    
    public String toJavaString() {
        String str = " bsh.console.println( \"> \"+";
        Expression e = (Expression) Services.getService().getModelMapping().get(writableObject);
        str += e.toJavaString();
        str += ");";
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