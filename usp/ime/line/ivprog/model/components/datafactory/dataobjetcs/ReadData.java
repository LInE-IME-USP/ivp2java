
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

public class ReadData extends CodeComponent {

  private String writableObject = "";
  public static final String STRING_CLASS = "read";

  public ReadData (String name, String description) {
    super(name, description);
    }

  /**
   * Return the printable object.
   * @return the printableObject
   */
  public String getWritableObject () {
    return writableObject;
    }

  /**
   * Set the printable object.
   * @param printableObject : the printableObject to set
   */
  public void setWritableObject (String printableObject) {
    this.writableObject = printableObject;
    }

  /**
   * Removes the printable object and return it.
   */
  public String removeWritableObject () {
    writableObject = "";
    return writableObject;
    }

  public String toXML () {
    Expression printable = (Expression) Services.getModelMapping().get(writableObject);
    String str = "<dataobject class=\"read\"><id>" + getUniqueID() + "</id>" + "<writable>" + printable.toXML()
            + "</writable></dataobject>";
    return str;
    }

  public String toJavaString () {
    String str = "if (!isEvaluating){";
    VariableReference varRef = (VariableReference) Services.getModelMapping().get(writableObject);
    System.out.println(">>>>>>>>>> varRef.getReferencedType()" + varRef.getReferencedType());
    str += getStrForType(varRef.getReferencedType(), varRef);
    str += "  }else{ " + varRef.toJavaString() + " = ";
    str += getElseEvaluationStrForType(varRef.getReferencedType());
    str += "  }";
    return str;
    }

  private String getElseEvaluationStrForType (short type) {
    String str = "";
    if (type == Expression.EXPRESSION_INTEGER) {
      str += "((Integer) new Integer(interpreterInput.pop())).intValue();";
      } else if (type == Expression.EXPRESSION_DOUBLE) {
      str += "((Double) new Double(interpreterInput.pop())).doubleValue();";
      } else if (type == Expression.EXPRESSION_BOOLEAN) {
      str += "((Boolean) new Boolean(interpreterInput.pop())).booleanValue();";
      } else if (type == Expression.EXPRESSION_STRING) {
      str += "new String(interpreterInput.pop());";
      }
    return str;
    }

  private String getStrForType (short type, VariableReference varRef) {
    String str = "";
    if (type == Expression.EXPRESSION_INTEGER) {
      str += "readInteger.showAskUser(\"" + varRef.getReferencedName() + "\");" + varRef.toJavaString()
              + " = readInteger.getFinalValue(); if(readInteger.isInterrupt()) return;";
      } else if (type == Expression.EXPRESSION_DOUBLE) {
      str += "readDouble.showAskUser(\"" + varRef.getReferencedName() + "\");" + varRef.toJavaString()
              + " = readDouble.getFinalValue(); if(readDouble.isInterrupt()) return;";
      } else if (type == Expression.EXPRESSION_BOOLEAN) {
      str += "readBoolean.showAskUser(\"" + varRef.getReferencedName() + "\");" + varRef.toJavaString()
              + " = readBoolean.getFinalValue(); if(readBoolean.isInterrupt()) return;";
      } else if (type == Expression.EXPRESSION_STRING) {
      str += "readString.showAskUser(\"" + varRef.getReferencedName() + "\");" + varRef.toJavaString()
              + " = readString.getFinalValue(); if(readString.isInterrupt()) return;";
      }
    return str;
    }

  public boolean equals (DomainObject o) {
    return false;
    }

  public void updateParent (String lastExp, String newExp, String operationContext) {
    if (writableObject == lastExp)
      writableObject = newExp;
    }

  }