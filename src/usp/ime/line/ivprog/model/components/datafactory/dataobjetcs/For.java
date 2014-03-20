package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.util.Vector;

import usp.ime.line.ivprog.model.utils.Services;

public class For extends CodeComposite {
    private String             indexExpression      = "";
    private String             lowerBoundExpression = "";
    private String             upperBoundExpression = "";
    private String             incrementExpression  = "";
    public static final String STRING_CLASS         = "for";
    public static int          FOR_MODE_1           = 0;
    public static int          FOR_MODE_2           = 1;
    public static int          FOR_MODE_3           = 2;
    private int                currentForMode       = 0;
    
    public For(String name, String description) {
        super(name, description);
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
        Expression index = (Expression) Services.getService().getModelMapping().get(indexExpression);
        Expression upper = (Expression) Services.getService().getModelMapping().get(upperBoundExpression);
        Expression lower = (Expression) Services.getService().getModelMapping().get(lowerBoundExpression);
        Expression inc = (Expression) Services.getService().getModelMapping().get(incrementExpression);
        String str = "";
        if (currentForMode == FOR_MODE_1) {
            str += "for(i" + getUniqueID() + "= 0; i" + getUniqueID() + " < " + upper.toJavaString() + "; i" + getUniqueID() + "++){\n";
        } else if (currentForMode == FOR_MODE_2) {
            str += "for(" + index.toJavaString() + " = 0; " + index.toJavaString() + "< " + upper.toJavaString() + "; " + index.toJavaString() + "++){\n";
        } else if (currentForMode == FOR_MODE_3) {
            str += "for(" + index.toJavaString() + " = " + lower.toJavaString() + "; " + index.toJavaString() + "< " + upper.toJavaString() + "; " + index.toJavaString() + "+=" + inc.toJavaString()
                    + ") {\n";
        }
        for (int i = 0; i < getChildrenList().size(); i++) {
            DataObject c = ((DataObject) Services.getService().getModelMapping().get(getChildrenList().get(i)));
            str += c.toJavaString()+"\n";
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
    
    public int getCurrentForMode() {
        return currentForMode;
    }
    
    public void setCurrentForMode(int currentForMode) {
        this.currentForMode = currentForMode;
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
}
