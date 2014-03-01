package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ExpressionTypeChanged extends DomainAction {
    private IVPProgram model;
    private String     holder;
    private String     expression;
    private boolean    isClean;
    private boolean    isComparison;
    private String     context;
    
    public ExpressionTypeChanged(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }
    
    protected void executeAction() {
        model.deleteExpression(expression, holder, context, isClean, isComparison, _currentState);
    }
    
    protected void undoAction() {
        model.restoreExpression(expression, holder, context, isClean, _currentState);
    }
    
    public boolean equals(DomainAction a) {
        return false;
    }
    
    public String getHolder() {
        return holder;
    }
    
    public void setHolder(String holder) {
        this.holder = holder;
    }
    
    public String getExpression() {
        return expression;
    }
    
    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    public void setContext(String ctx) {
        context = ctx;
    }
    
    public String getContext() {
        return context;
    }
    
    public boolean isClean() {
        return isClean;
    }
    
    public void setClean(boolean isClean) {
        this.isClean = isClean;
    }
    
    public boolean isComparison() {
        return isComparison;
    }
    
    public void setComparison(boolean isComparison) {
        this.isComparison = isComparison;
    }
    
    public String toString(){
        String str = "";
        str +=  "<expressiontypechanged>\n" +
                "   <holder>"+holder+"</holder>\n"+
                "   <expression>"+expression+"</expression>\n"+
                "   <isclean>"+isClean+"</isclean>\n"+
                "   <iscomparison>"+isComparison+"</iscomparison>\n"+
                "   <context>"+context+"</context>\n"+
                "</expressiontypechanged>\n";
        return str;
    }
}
