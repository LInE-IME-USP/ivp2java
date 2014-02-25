package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class Print extends CodeComponent {
    private String             printableObjectID = null;
    public static final String STRING_CLASS      = "print";
    
    public Print(String name, String description) {
        super(name, description);
    }
    
    public Print() {
        super(STRING_CLASS, STRING_CLASS);
    }
    
    /**
     * Return the printable object.
     * 
     * @return the printableObject
     */
    public String getPrintableObject() {
        return printableObjectID;
    }
    
    /**
     * Set the printable object.
     * 
     * @param printableObject
     *            the printableObject to set
     */
    public void setPrintableObject(String printableObject) {
        this.printableObjectID = printableObject;
    }
    
    /**
     * Removes the printable object and return it.
     */
    public String removePrintableObject() {
        printableObjectID = null;
        return printableObjectID;
    }
    
    public String toXML() {
        Expression printable = (Expression) Services.getService().getModelMapping().get(printableObjectID);
        String str = "<dataobject class=\"print\"><id>" + getUniqueID() + "</id>" + "<printable>" + printable.toXML() + "</printable></dataobject>";
        return str;
    }
    
    public String toJavaString() {
        String str = " bsh.console.println( \"> \"+";
        Expression e = (Expression) Services.getService().getModelMapping().get(printableObjectID);
        str += e.toJavaString();
        str += ");";
        return str;
    }
    
    public boolean equals(DomainObject o) {
        return false;
    }
    
    public void updateParent(String lastExp, String newExp, String operationContext) {
        if (printableObjectID == lastExp)
            printableObjectID = newExp;
    }
}
