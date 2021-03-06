
/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.model.utils.Services;

public class ReturnStatement extends CodeComponent {

  private String expressionToBeReturnedID = "";
  private short type = -1;
  public static final String STRING_CLASS = "returnstatement";

  public ReturnStatement (String name, String description) {
    super(name, description);
    }

  /**
   * Return the expression to be returned by the return statement.
   * @return
   */
  public String getExpressionToBeReturned () {
    return expressionToBeReturnedID;
    }

  /**
   * Set the expression to be returned by the return statement.
   * @param expressionToBeReturned
   */
  public void setExpressionToBeReturned (String expressionToBeReturned) {
    this.expressionToBeReturnedID = expressionToBeReturned;
    }

  /**
   * Get returnStatement type.
   * @return
   */
  public short getType () {
    return type;
    }

  /**
   * Set returnStatement type.
   * @param type
   */
  public void setType (short type) {
    this.type = type;
    }

  public String toXML () {
    Expression expToBeReturned = (Expression) Services.getModelMapping().get(expressionToBeReturnedID);
    String str = "<dataobject class=\"returnstatement\">" + "<id>" + getUniqueID() + "</id>" + "<expression>" + expToBeReturned.toXML()
            + "</expression>" + "</dataobject>";
    return str;
    }

  public String toJavaString () {
    Expression expToBeReturned = (Expression) Services.getModelMapping().get(expressionToBeReturnedID);
    return " return " + expToBeReturned.toJavaString() + "; ";
    }

  public boolean equals (DomainObject o) {
    return false;
    }

  public void updateParent (String lastExp, String newExp, String operationContext) {
    if (expressionToBeReturnedID == lastExp)
      expressionToBeReturnedID = newExp;
    }

  }
