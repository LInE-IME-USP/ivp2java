package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

public class Constant extends Expression {

    private String             constantValue = "";
    public static final String STRING_CLASS  = "constant";

    public Constant(String name, String description) {
        super(name, description);
    }

    public String toXML() {
        return null;
    }

    public String toJavaString() {
        if(getExpressionType() == EXPRESSION_STRING){
            return "\""+constantValue+"\"";
        }
        return constantValue;
    }

    public boolean equals(DomainObject o) {
        return false;
    }

    public String getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(String constantValue) {
        this.constantValue = constantValue;
    }

}
