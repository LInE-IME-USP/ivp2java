
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

public class While extends CodeComposite {

  private String conditionID = "";
  public static final String STRING_CLASS = "while";

  public While (String name, String description) {
    super(name, description);
    }

  /**
   * Return the loop condition.
   */
  public String getCondition () {
    return conditionID;
    }

  /**
   * Set the loop condition.
   * @param cond
   */
  public void setCondition (String cond) {
    conditionID = cond;
    }

  public String toXML () {
    Operation operation = (Operation) Services.getModelMapping().get(conditionID);
    String str = "<dataobject class=\"while\"><id>" + getUniqueID() + "</id>" + "<condition>" + operation.toXML()
            + "</condition><children>";
    for (int i = 0; i < getChildrenList().size(); i++) {
      CodeComposite aChild = (CodeComposite) Services.getModelMapping().get((String) getChildAtIndex(i));
      str += aChild.toXML();
      }
    str += "</children></dataobject>";
    return str;
    }

  public String toJavaString () {
    // -- adding :
    //    while
    Expression e = ((Expression) Services.getModelMapping().get(conditionID));
    String str = " while (";
    str += e.toJavaString();
    str += ") {";
    for (int i = 0; i < getChildrenList().size(); i++) {
      DataObject c = ((DataObject) Services.getModelMapping().get(getChildrenList().get(i)));
      str += c.toJavaString();
      }
    str += "  }";
    // 
    // converting condition
    return str;
    }

  public boolean equals (DomainObject o) {
    return false;
    }

  public void updateParent (String lastExp, String newExp, String operationContext) {
    conditionID = newExp;
    }

  }
