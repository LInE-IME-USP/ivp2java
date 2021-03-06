
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

package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class StringOperationUI extends OperationUI {

  public StringOperationUI (String parent, String scope, String id) {
    super(parent, scope, id);
    }

  public void operationTypeChanged (String id, String context) {
    if (currentModelID.equals(id) && this.context.equals(context)) {
      setModelID(id);
      }
    }

  public void initOperationSignMenu () {
    operationSignMenu = new JPopupMenu();
    Action changeToAddition = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_EXPRESSION_OPERATION_CONCAT;");
        Services.getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_CONCAT, context);
        }
      };
    changeToAddition.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
    changeToAddition.putValue(Action.NAME, "\u002B");
    operationSignMenu.add(changeToAddition);
    }

  public void initSignal () {
    String sign = "";
    Operation op = (Operation) Services.getModelMapping().get(currentModelID);
    short type = op.getOperationType();
    if (type == Expression.EXPRESSION_OPERATION_CONCAT) {
      sign = "\u002B";
      }
    expSign.setText(sign);
    }

  public boolean isContentSet () {
    boolean isCSet = true;
    if (!expressionBaseUI_1.isCSet()) {
      isCSet = false;
      }
    if (!expressionBaseUI_2.isCSet()) {
      if (isCSet) {
        isCSet = false;
        }
      }
    return isCSet;
    }

  public void lockDownCode () {
    disableEdition();
    }

  }
