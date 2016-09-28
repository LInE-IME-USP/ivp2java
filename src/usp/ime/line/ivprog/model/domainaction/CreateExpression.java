/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * Create the expression (with operators and listeners)
 *
 * @see: 
 * 
 */

package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateExpression extends DomainAction {

  private IVPProgram model;
  private String holder;
  private String lastExpression;
  private String newExpression;
  private String removedExpression;
  private String context;
  private short expressionType;
  private short primitiveType;

  public CreateExpression (String name, String description) {
    super(name, description);
    }

  public void setDomainModel (DomainModel m) {
    model = (IVPProgram) m;
    }

  // Called by: ilm/framework/assignment/model/DomainAction.java: 'execute()' or 'executeInSilence()'
  // The variable 'executingInSilence' is to indicate that is reading from file
  protected void executeAction () {
    if (executingInSilence) { // from iLM framework : executingInSilence = true => is reading file
      int myId = Integer.parseInt(newExpression);
      if (myId > _currentState.getData().getDataFactory().getObjectID()) {
        _currentState.getData().getDataFactory().setObjectID(myId);
        }
      }
    if (isRedo()) {
      model.restoreExpression(removedExpression, holder, context, false, _currentState);
      }
    else {
      newExpression = model.createExpression(lastExpression, holder, expressionType, primitiveType, context, _currentState);
      }
    }

  protected void undoAction() {
    removedExpression = model.deleteExpression(newExpression, holder, context, false, false, _currentState);
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

  public String getExp1() {
    return lastExpression;
    }

  public void setExp1(String exp1) {
    this.lastExpression = exp1;
    }

  public short getExpressionType() {
    return expressionType;
    }

  public void setExpressionType(short expressionType) {
    this.expressionType = expressionType;
    }

  public void setContext(String ctx) {
    context = ctx;
    }

  public String getContext() {
    return context;
    }

  public short getPrimitiveType() {
    return primitiveType;
    }

  public void setPrimitiveType(short primitiveType) {
    this.primitiveType = primitiveType;
    }

  public String getRemovedExpression() {
    return removedExpression;
    }

  public void setRemovedExpression(String removedExpression) {
    this.removedExpression = removedExpression;
    }

  public String getNewExpression() {
    return newExpression;
    }

  public void setNewExpression(String newExpression) {
    this.newExpression = newExpression;
    }

  public String toString() {
    String str = "";
    str += "<createexpression>\n" + "   <holder>" + holder + "</holder>\n" + "   <lastexpression>" + lastExpression
            + "</lastexpression>\n" + "   <newexpression>" + newExpression + "</newexpression>\n" + "   <removedexpression>"
            + removedExpression + "</removedexpression>\n" + "   <context>" + context + "</context>\n" + "   <expressiontype>"
            + expressionType + "</expressiontype>\n" + "   <primitivetype>" + primitiveType + "</primitivetype>\n"
            + "</createexpression>\n";
    return str;
    }

  }
