package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.util.Vector;

import usp.ime.line.ivprog.Services;

public class IfElse extends CodeComposite {
    private Vector             elseChildren = new Vector();
    private String             comparisonID = null;
    public static final String STRING_CLASS = "ifelse";
    
    public IfElse(String name, String description) {
        super(name, description);
    }
    
    /**
     * Append a child at the specified position of elseChildren's vector.
     * 
     * @param aChild
     */
    public void addElseChildToIndex(String aChildID, int index) {
        elseChildren.add(index, aChildID);
    }
    
    /**
     * Remove a child from the specified position and return it.
     * 
     * @param index
     */
    public String removeElseChildFromIndex(int index) {
        String elseChild = (String) elseChildren.get(index);
        elseChildren.remove(index);
        return elseChild;
    }
    
    /**
     * Remove the specified child from the children vector and return it.
     * 
     * @param child
     */
    public int removeElseChild(String childID) {
        int index = elseChildren.indexOf(childID);
        elseChildren.remove(childID);
        return index;
    }
    
    /**
     * Return the child at the specified position.
     * 
     * @param index
     * @return
     */
    public String getElseChildAtIndex(int index) {
        return (String) elseChildren.get(index);
    }
    
    /**
     * Return the if/else condition.
     * 
     * @return
     */
    public String getComparisonID() {
        return comparisonID;
    }
    
    /**
     * Set the if/else condition.
     * 
     * @param comp
     */
    public void setComparison(String compID) {
        comparisonID = compID;
    }
    
    public String toXML() {
        Operation comp = (Operation) Services.getService().getModelMapping().get(comparisonID);
        String str = "<dataobject class=\"ifelse\">" + "<id>" + getUniqueID() + "</id>" + "<comparison>" + comp.toXML() + "</comparison>" + "<ifchildren>";
        for (int i = 0; i < getChildrenList().size(); i++) {
            CodeComposite child = (CodeComposite) Services.getService().getModelMapping().get((String) getChildrenList().get(i));
            str += child.toXML();
        }
        str += "</ifchildren><elsechildren>";
        for (int i = 0; i < elseChildren.size(); i++) {
            CodeComposite child = (CodeComposite) Services.getService().getModelMapping().get((String) elseChildren.get(i));
            str += child.toXML();
        }
        str += "</elsechildren></dataobject>";
        return str;
    }
    
    public String toJavaString() {
        Expression e = ((Expression) Services.getService().getModelMapping().get(comparisonID));
        String str = " if (";
        str += e.toJavaString();
        str += ") {";
        for (int i = 0; i < getChildrenList().size(); i++) {
            DataObject c = ((DataObject) Services.getService().getModelMapping().get(getChildrenList().get(i)));
            str += c.toJavaString();
        }
        str += "} else {";
        for (int i = 0; i < elseChildren.size(); i++) {
            DataObject c = ((DataObject) Services.getService().getModelMapping().get(elseChildren.get(i)));
            str += c.toJavaString();
        }
        str += "}";
        return str;
    }
    
    public boolean equals(DomainObject o) {
        return false;
    }
    
    public void updateParent(String lastExp, String newExp, String operationContext) {
        if (comparisonID == lastExp)
            comparisonID = newExp;
    }
    
    public String getChildContext(String childID) {
        if (elseChildren.contains(childID))
            return "else";
        else if (getChildrenList().contains(childID))
            return "if";
        else
            return "";
    }
}
