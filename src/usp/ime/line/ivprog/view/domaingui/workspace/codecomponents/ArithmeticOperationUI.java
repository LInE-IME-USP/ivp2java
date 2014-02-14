package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
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
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToAddition.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToAddition.putValue(Action.NAME, "\u002B");
        Action changeToDivision = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_DIVISION, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToDivision.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToDivision.putValue(Action.NAME, "\u00F7");
        Action changeToMultiplication = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_MULTIPLICATION, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToMultiplication.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToMultiplication.putValue(Action.NAME, "\u00D7");
        Action changeToSubtraction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_SUBTRACTION, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToSubtraction.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ArithmeticOperationUI.changeSignPanel.tip"));
        changeToSubtraction.putValue(Action.NAME, "\u002D");
        operationSignMenu.add(changeToAddition);
        operationSignMenu.add(changeToDivision);
        operationSignMenu.add(changeToMultiplication);
        operationSignMenu.add(changeToSubtraction);
    }
    
    public void initSignal() {
        String sign = null;
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
        }
        expSign.setText(sign);
    }
    
    public void operationTypeChanged(String id, String context) {
        if (currentModelID.equals(id) && this.context.equals(context)) {
            setModelID(id);
        }
    }
}
