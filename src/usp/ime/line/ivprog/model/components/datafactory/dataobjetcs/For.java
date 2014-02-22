package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.util.Vector;

import usp.ime.line.ivprog.Services;

public class For extends CodeComposite {
    private String             indexExpression      = "";
    private String             lowerBoundExpression = "";
    private String             upperBoundExpression = "";
    private String             incrementExpression  = "";
    public static final String STRING_CLASS         = "for";
    private static int forCount = 0;
    private int indexCount;
    
    public For(String name, String description) {
        super(name, description);
        setIndexCount(forCount);
        setForCount(getForCount() + 1);
    }
    
    /**
     * Returns the expression containing index initial value.
     * 
     * @return
     */
    public String getIndexExpression() {
        return indexExpression;
    }
    
    /**
     * Set the index initial value expression.
     * 
     * @param indexInitValue
     */
    public void setIndexExpression(String indexInitValue) {
        indexExpression = indexInitValue;
    }
    
    /**
     * Return the index upper bound's expression.
     * 
     * @return
     */
    public String getUpperBoundExpression() {
        return upperBoundExpression;
    }
    
    /**
     * Sets the upper bound's expression.
     * 
     * @param hBound
     */
    public void setUpperBoundExpression(String hBound) {
        upperBoundExpression = hBound;
    }
    
    /**
     * Return the expression that defines the increment's value.
     * 
     * @return
     */
    public String getIncrementExpression() {
        return incrementExpression;
    }
    
    /**
     * Set the increment's value.
     * 
     * @param inc
     */
    public void setIncrementExpression(String inc) {
        incrementExpression = inc;
    }
    
    public String toXML() {
        Expression index = (Expression) Services.getService().getModelMapping().get(indexExpression);
        Expression upper = (Expression) Services.getService().getModelMapping().get(upperBoundExpression);
        Expression inc = (Expression) Services.getService().getModelMapping().get(incrementExpression);
        String str = "<dataobject class=\"for\">" + "<id>" + getUniqueID() + "</id>" + "<initialvalue>" + index.toXML() + "</initialvalue>" + "<upperbound>" + upper.toXML() + "</upperbound>"
                + "<increment>" + inc.toXML() + "</increment>" + "<children>";
        Vector children = getChildrenList();
        for (int i = 0; i < children.size(); i++) {
            str += ((DataObject) Services.getService().getModelMapping().get((String) children.get(i))).toXML();
        }
        str += "</children></dataobject>";
        return str;
    }
    
    public String toJavaString() {
        Variable index = (Variable) Services.getService().getModelMapping().get(indexExpression);
        Expression upper = (Expression) Services.getService().getModelMapping().get(upperBoundExpression);
        Expression lower = (Expression) Services.getService().getModelMapping().get(lowerBoundExpression);
        Expression inc = (Expression) Services.getService().getModelMapping().get(incrementExpression);
        String str = "";
        str+=" for ( "+index.getVariableName()+" = "+lower.toJavaString()+";"+index.getVariableName()+" < " + upper.toJavaString() + "; " + index.getVariableName() + " += " + inc.toJavaString() + " ) {";
        for (int i = 0; i < getChildrenList().size(); i++) {
            DataObject c = ((DataObject) Services.getService().getModelMapping().get(getChildrenList().get(i)));
            str += c.toJavaString();
        }
        str += "}";
        return str;
    }
    
    public boolean equals(DomainObject o) {
        return false;
    }
    
    public void updateParent(String lastExp, String newExp, String operationContext) {
        if (upperBoundExpression.equals(lastExp))
            upperBoundExpression = newExp;
        else if (incrementExpression.equals(lastExp))
            incrementExpression = newExp;
        else if (indexExpression.equals(lastExp))
            indexExpression = newExp;
        else if (incrementExpression.equals(lastExp))
            incrementExpression = newExp;
    }
    
    public String getLowerBoundExpression() {
        return lowerBoundExpression;
    }
    
    public void setLowerBoundExpression(String lowerBoundExpression) {
        this.lowerBoundExpression = lowerBoundExpression;
    }
    
    public void removeExpression(String expression, String context) {
        if (context.equals("forUpperBound")) {
            upperBoundExpression = "";
        } else if (context.equals("forIndex")) {
            indexExpression = "";
        } else if (context.equals("forLowerBound")) {
            lowerBoundExpression = "";
        } else if (context.equals("forIncrement")) {
            incrementExpression = "";
        }
    }

    public static int getForCount() {
        return forCount;
    }

    public static void setForCount(int forCount) {
        For.forCount = forCount;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }
}
