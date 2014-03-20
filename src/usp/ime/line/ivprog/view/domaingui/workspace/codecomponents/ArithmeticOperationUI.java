package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ArithmeticOperationUI extends OperationUI {
    public ArithmeticOperationUI(String parent, String scope, String id) {
        super(parent, scope, id);
    }
    
    public void initOperationSignMenu() {
        operationSignMenu = new JPopupMenu();
        Action changeToAddition = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_ADDITION, context);
                Tracking.getInstance().track("event=CLICK;where=BTN_EXPRESSION_OPERATION_ADDITION;");
            }
        };
        changeToAddition.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToAddition.putValue(Action.NAME, "\u002B");
        Action changeToDivision = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_DIVISION, context);
                Tracking.getInstance().track("event=CLICK;where=BTN_EXPRESSION_OPERATION_DIVISION;");
            }
        };
        changeToDivision.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToDivision.putValue(Action.NAME, "\u00F7");
        Action changeToMultiplication = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_MULTIPLICATION, context);
                Tracking.getInstance().track("event=CLICK;where=BTN_EXPRESSION_OPERATION_MULTIPLICATION;");
            }
        };
        changeToMultiplication.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToMultiplication.putValue(Action.NAME, "\u00D7");
        Action changeToSubtraction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_SUBTRACTION, context);
                Tracking.getInstance().track("event=CLICK;where=BTN_EXPRESSION_OPERATION_SUBTRACTION;");
            }
        };
        changeToSubtraction.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToSubtraction.putValue(Action.NAME, "\u002D");
        Action changeToIntDiv = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_INTDIV, context);
                Tracking.getInstance().track("event=CLICK;where=BTN_EXPRESSION_OPERATION_INTDIV;");
            }
        };
        changeToIntDiv.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToIntDiv.putValue(Action.NAME, "\u0025");
        operationSignMenu.add(changeToAddition);
        operationSignMenu.add(changeToDivision);
        operationSignMenu.add(changeToMultiplication);
        operationSignMenu.add(changeToSubtraction);
        operationSignMenu.addSeparator();
        operationSignMenu.add(changeToIntDiv);
    }
    
    public void initSignal() {
        String sign = "";
        Operation op = (Operation) Services.getService().getModelMapping().get(currentModelID);
        short type = op.getOperationType();
        if (type == Expression.EXPRESSION_OPERATION_ADDITION) {
            sign = "\u002B";
        } else if (type == Expression.EXPRESSION_OPERATION_DIVISION) {
            sign = "\u00F7";
        } else if (type == Expression.EXPRESSION_OPERATION_MULTIPLICATION) {
            sign = "\u00D7";
        } else if (type == Expression.EXPRESSION_OPERATION_SUBTRACTION) {
            sign = "\u002D";
        } else if (type == Expression.EXPRESSION_OPERATION_INTDIV) {
            sign = "\u0025";
        }
        expSign.setText(sign);
    }
    
    public void operationTypeChanged(String id, String context) {
        if (currentModelID.equals(id) && this.context.equals(context)) {
            setModelID(id);
        }
    }
    
    public boolean isContentSet() {
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
    
    public void lockDownCode() {
        disableEdition();
    }
}
