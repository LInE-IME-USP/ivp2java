package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class BooleanOperationUI extends OperationUI {
    private JPopupMenu operationAndOrMenu;
    private JPopupMenu operationComparisonMenu;
    
    public BooleanOperationUI(String parent, String scope, String id) {
        super(parent, scope, id);
    }
    
    public void initOperationSignMenu() {
        operationComparisonMenu = new JPopupMenu();
        operationAndOrMenu = new JPopupMenu();
        Action changeToAND = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_AND, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToAND.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.AND.tip"));
        changeToAND.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.AND.text"));
        Action changeToOR = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_OR, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToOR.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.OR.tip"));
        changeToOR.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.OR.text"));
        Action changeToLEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_LEQ, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToLEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LEQ.tip"));
        changeToLEQ.putValue(Action.NAME, "\u2264");
        Action changeToLES = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_LES, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToLES.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LES.tip"));
        changeToLES.putValue(Action.NAME, "\u003C");
        Action changeToEQU = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_EQU, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToEQU.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.EQU.tip"));
        changeToEQU.putValue(Action.NAME, "\u003D\u003D");
        Action changeToNEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_NEQ, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToNEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.NEQ.tip"));
        changeToNEQ.putValue(Action.NAME, "\u2260");
        Action changeToGEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_GEQ, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToGEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.GEQ.tip"));
        changeToGEQ.putValue(Action.NAME, "\u2265");
        Action changeToGRE = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().changeExpressionSign(currentModelID, Expression.EXPRESSION_OPERATION_GRE, context);
            }
        };
        // setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
        changeToGRE.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.GRE.tip"));
        changeToGRE.putValue(Action.NAME, "\u003E");
        operationComparisonMenu.add(changeToLEQ);
        operationComparisonMenu.add(changeToLES);
        operationComparisonMenu.add(changeToEQU);
        operationComparisonMenu.add(changeToNEQ);
        operationComparisonMenu.add(changeToGEQ);
        operationComparisonMenu.add(changeToGRE);
        operationAndOrMenu.add(changeToAND);
        operationAndOrMenu.add(changeToOR);
        operationSignMenu = operationComparisonMenu;
    }
    
    public void initSignal() {
        String sign = null;
        Operation op = (Operation) Services.getService().getModelMapping().get(currentModelID);
        short type = op.getOperationType();
        if (type == Expression.EXPRESSION_OPERATION_AND) {
            sign = ResourceBundleIVP.getString("BooleanOperationUI.AND.text");
            operationSignMenu = operationAndOrMenu;
            enableComparison();
        } else if (type == Expression.EXPRESSION_OPERATION_OR) {
            sign = ResourceBundleIVP.getString("BooleanOperationUI.OR.text");
            operationSignMenu = operationAndOrMenu;
            enableComparison();
        } else if (type == Expression.EXPRESSION_OPERATION_LEQ) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u2264";
        } else if (type == Expression.EXPRESSION_OPERATION_LES) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u003C";
        } else if (type == Expression.EXPRESSION_OPERATION_EQU) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u003D\u003D";
        } else if (type == Expression.EXPRESSION_OPERATION_NEQ) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u2260";
        } else if (type == Expression.EXPRESSION_OPERATION_GEQ) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u2265";
        } else if (type == Expression.EXPRESSION_OPERATION_GRE) {
            disableComparison();
            operationSignMenu = operationComparisonMenu;
            sign = "\u003E";
        }
        expSign.setText(sign);
    }
    
    private void enableComparison() {
        expressionBaseUI_1.enableComparison();
        expressionBaseUI_2.enableComparison();
    }
    
    private void disableComparison() {
        expressionBaseUI_1.disableComparison();
        expressionBaseUI_2.disableComparison();
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
            if (isCSet)
                isCSet = false;
        }
        return isCSet;
    }
    
    public void lockDownCode() {
        disableEdition();
    }
}
