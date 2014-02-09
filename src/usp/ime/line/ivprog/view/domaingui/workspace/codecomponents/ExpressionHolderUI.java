package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditBoolean;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditInPlace;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class ExpressionHolderUI extends JPanel implements IExpressionListener {

    public static final Color borderColor         = new Color(230, 126, 34);
    public static final Color hoverColor          = FlatUIColors.HOVER_COLOR;

    private boolean           drawBorder          = true;
    private boolean           isComparisonEnabled = false;
    private boolean           isEditing           = false;
    private boolean           isContentSet        = false;
    private boolean           isComparison        = false;
    private JPopupMenu        contentMenu;
    private JPopupMenu        operationMenuWithoutComparison;
    private JPopupMenu        operationMenuWithComparison;
    private JPopupMenu        operationMenuComparison;

    private JLabel            selectLabel;
    private String            parentModelID;
    private String            scopeModelID;
    private String            currentModelID;
    private String            operationContext;
    private JButton           operationsBtn;
    private JComponent        expression;

    public ExpressionHolderUI(String parent, String scopeID) {
        init(parent, scopeID);
        initialization();
        initComponents();
    }

    // BEGIN: initialization methods
    private void init(String parent, String scopeID) {
        parentModelID = parent;
        scopeModelID = scopeID;
        currentModelID = "";
        setOperationContext("");
        Services.getService().getController().getProgram().addExpressionListener(this);
    }

    private void initialization() {
        setBackground(FlatUIColors.MAIN_BG);
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setHgap(3);
        flowLayout.setVgap(0);
        setLayout(flowLayout);
        addMouseListener(new ExpressionMouseListener(this));
    }

    private void initComponents() {
        initLabel();
        initChooseContentMenu();
        initChangeContentBtn();
        initOperationsMenu();
    }

    private void initOperationsMenu() {
        operationMenuWithoutComparison = new JPopupMenu();
        operationMenuWithComparison = new JPopupMenu();
        operationMenuComparison = new JPopupMenu();
        addBooleanOperators(operationMenuComparison);
        addArithmeticOperations(operationMenuWithComparison);
        addArithmeticOperations(operationMenuWithoutComparison);
        addComparison(operationMenuWithComparison);
        addCleanContentForCondition(operationMenuComparison);
        addCleanContent(operationMenuWithComparison);
        addCleanContent(operationMenuWithoutComparison);
    }

    private void addCleanContentForCondition(JPopupMenu menu) {
        Action cleanContent = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().deleteExpression(expressionID, parentModelID, operationContext, true, true);
            }
        };
        cleanContent.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.tip"));
        cleanContent.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.text"));
        menu.add(cleanContent);
    }

    private void addBooleanOperators(JPopupMenu menu) {
        Action changeToAND = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_AND, operationContext);
            }
        };
        changeToAND.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.AND.tip"));
        changeToAND.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.AND.text"));
        Action changeToOR = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_OR, operationContext);
            }
        };
        changeToOR.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.OR.tip"));
        changeToOR.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.OR.text"));
        menu.add(changeToAND);
        menu.add(changeToOR);
    }

    private void addCleanContent(JPopupMenu menu) {
        Action cleanContent = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().deleteExpression(expressionID, parentModelID, operationContext, true, false);
            }
        };
        cleanContent.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.tip"));
        cleanContent.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.text"));
        menu.add(cleanContent);
    }

    private void addArithmeticOperations(JPopupMenu menu) {
        Action createAddition = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_ADDITION, operationContext);
            }
        };
        createAddition.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.tip"));
        createAddition.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.text"));
        Action createSubtraction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_SUBTRACTION, operationContext);
            }
        };
        createSubtraction.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.tip"));
        createSubtraction.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.text"));
        Action createMultiplication = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_MULTIPLICATION, operationContext);
            }
        };
        createMultiplication.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.tip"));
        createMultiplication.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.text"));
        Action createDivision = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_DIVISION, operationContext);
            }
        };
        createDivision.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.createDivision.tip"));
        createDivision.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createDivision.text"));
        menu.add(createAddition);
        menu.add(createSubtraction);
        menu.add(createDivision);
        menu.add(createMultiplication);
        menu.addSeparator();
    }

    private void addComparison(JPopupMenu menu) {
        Action changeToLEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_LEQ, operationContext);
            }
        };
        changeToLEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LEQ.tip"));
        changeToLEQ.putValue(Action.NAME, "\u2264");
        Action changeToLES = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_LES, operationContext);
            }
        };
        changeToLES.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LES.tip"));
        changeToLES.putValue(Action.NAME, "\u003C");
        Action changeToEQU = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_EQU, operationContext);
            }
        };
        changeToEQU.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.EQU.tip"));
        changeToEQU.putValue(Action.NAME, "=");
        Action changeToNEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_NEQ, operationContext);
            }
        };
        changeToNEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.NEQ.tip"));
        changeToNEQ.putValue(Action.NAME, "\u2260");
        Action changeToGEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_GEQ, operationContext);
            }
        };
        changeToGEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.GEQ.tip"));
        changeToGEQ.putValue(Action.NAME, "\u2265");
        Action changeToGRE = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_GRE, operationContext);
            }
        };
        changeToGRE.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.GRE.tip"));
        changeToGRE.putValue(Action.NAME, "\u003E");
        menu.add(changeToLEQ);
        menu.add(changeToLES);
        menu.add(changeToEQU);
        menu.add(changeToNEQ);
        menu.add(changeToGEQ);
        menu.add(changeToGRE);
        menu.addSeparator();
    }

    private void initChooseContentMenu() {
        contentMenu = new JPopupMenu();
        Action variableHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().createExpression(null, parentModelID, Expression.EXPRESSION_VARIABLE, operationContext);
            }
        };
        variableHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.variableHasBeenChosen.tip"));
        variableHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.variableHasBeenChosen.text"));
        Action integerHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().createExpression(null, parentModelID, Expression.EXPRESSION_INTEGER, operationContext);
            }
        };
        integerHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.integerHasBeenChosen.tip"));
        integerHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.integerHasBeenChosen.text"));
        Action doubleHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().createExpression(null, parentModelID, Expression.EXPRESSION_DOUBLE, operationContext);
            }
        };
        doubleHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.doubleHasBeenChosen.tip"));
        doubleHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.doubleHasBeenChosen.text"));
        Action textHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().createExpression(null, parentModelID, Expression.EXPRESSION_STRING, operationContext);
            }
        };
        textHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.textHasBeenChosen.tip"));
        textHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.textHasBeenChosen.text"));
        Action booleanHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().createExpression(null, parentModelID, Expression.EXPRESSION_STRING, operationContext);
            }
        };
        booleanHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionBaseUI.action.booleanHasBeenChosen.tip"));
        booleanHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.booleanHasBeenChosen.text"));
        contentMenu.add(variableHasBeenChosen);
        contentMenu.add(integerHasBeenChosen);
        contentMenu.add(doubleHasBeenChosen);
        contentMenu.add(textHasBeenChosen);
        contentMenu.add(booleanHasBeenChosen);
    }

    private void initChangeContentBtn() {
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!isComparison) {
                    if (isComparisonEnabled) {
                        operationMenuWithComparison.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                    } else {
                        operationMenuWithoutComparison.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                    }
                } else {
                    operationMenuComparison.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                }
            }
        };
        action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/operations.png")));
        operationsBtn = new JButton(action);
        operationsBtn.setUI(new IconButtonUI());
        operationsBtn.setVisible(false);
        add(operationsBtn);
    }

    private void initLabel() {
        selectLabel = new JLabel(ResourceBundleIVP.getString("ExpressionBaseUI.selectLabel.text"));
        selectLabel.setForeground(FlatUIColors.CHANGEABLE_ITEMS_COLOR);
        add(selectLabel);
    }

    // END: initialization methods

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawBorder) {
            FlowLayout layout = (FlowLayout) getLayout();
            layout.setVgap(3);
            layout.setHgap(3);
            revalidate();
            g.setColor(borderColor);
            java.awt.Rectangle bounds = getBounds();
            for (int i = 0; i < bounds.width; i += 6) {
                g.drawLine(i, 0, i + 3, 0);
                g.drawLine(i + 3, bounds.height - 1, i + 6, bounds.height - 1);
            }
            for (int i = 0; i < bounds.height; i += 6) {
                g.drawLine(0, i, 0, i + 3);
                g.drawLine(bounds.width - 1, i + 3, bounds.width - 1, i + 6);
            }
        } else {
            FlowLayout layout = (FlowLayout) getLayout();
            layout.setVgap(0);
            layout.setHgap(0);
        }
    }

    // BEGIN: Mouse listener
    private class ExpressionMouseListener implements MouseListener {
        private JPanel container;

        public ExpressionMouseListener(JPanel c) {
            container = c;
        }

        public void mouseEntered(MouseEvent e) {
            if (isEditing && !isContentSet) {
                setBackground(hoverColor);
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        public void mouseExited(MouseEvent e) {
            if (isEditing && !isContentSet) {
                setBackground(FlatUIColors.MAIN_BG);
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        }

        public void mouseClicked(MouseEvent arg0) {
            if (isEditing && !isContentSet) {
                contentMenu.show(container, 0, container.getHeight());
                contentMenu.requestFocus();
            }
        }

        public void mousePressed(MouseEvent arg0) {
        }

        public void mouseReleased(MouseEvent arg0) {
        }
    }

    // END: Mouse listener

    // BEGIN: Expression listener methods
    public void expressionCreated(String holder, String id, String context) {
        if (holder == parentModelID && operationContext.equals(context)) {
            JComponent lastExp = expression;
            if (expression != null)
                remove(expression);
            currentModelID = id;
            expression = Services.getService().getRenderer().paint(id);
            populatedExpressionHolder();
            add(expression, 0);
            if (expression instanceof VariableSelectorUI) {
                if (isEditing)
                    ((VariableSelectorUI) expression).editStateOn();
                else {
                    ((VariableSelectorUI) expression).editStateOff("");
                }
            } else if (expression instanceof ConstantUI) {
                if (isEditing)
                    ((ConstantUI) expression).editStateOn();
                else {
                    ((ConstantUI) expression).editStateOff("");
                }
            } else {
                if (!isComparison) {
                    if (lastExp != null)
                        ((OperationUI) expression).setExpressionBaseUI_1(lastExp);
                }
                if (isEditing) {
                    ((OperationUI) expression).enableEdition();
                } else {
                    ((OperationUI) expression).disableEdition();
                }
                ((IDomainObjectUI) lastExp).setModelParent(currentModelID);
            }
            
            if (Services.getService().getViewMapping().get(parentModelID) instanceof CodeBaseUI) { // nessa atualização tenho que olhar o contexto...
                if (lastExp != null) {
                    Services.getService().getController().updateParent(parentModelID, ((IDomainObjectUI) lastExp).getModelID(), id, operationContext);
                }else{
                    Services.getService().getController().updateParent(parentModelID, "", id, operationContext);
                }
            }
            isContentSet = true;
            revalidate();
            repaint();
        }
    }

    public void expressionDeleted(String id, String context, boolean isClean) {
        if (expression != null) {
            if (currentModelID.equals(id) && operationContext.equals(context)) {
                remove(expression);
                isContentSet = false;
                if (!isClean) {
                    if (expression instanceof OperationUI) {
                        JComponent exp = ((OperationUI) expression).getExpressionBaseUI_1().getExpression();
                        currentModelID = ((IDomainObjectUI) exp).getModelID();
                        setExpression(exp);
                    } else {
                        emptyExpressionHolder();
                        currentModelID = "";
                    }
                }
                revalidate();
                repaint();

            }
        }

    }

    public void expressionRestoredFromCleaning(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && operationContext.equals(context)) {
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            setExpression(restoredExp);
            isContentSet = true;
        }
        revalidate();
        repaint();
    }

    public void expressionRestored(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && operationContext.equals(context)) {
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            if (restoredExp instanceof OperationUI) {
                ((OperationUI) restoredExp).setExpressionBaseUI_1(expression);
            } else {
                if (((VariableSelectorUI) restoredExp).isEditState()) {
                    editStateOn();
                } else {
                    editStateOff();
                }
            }
            setExpression(restoredExp);
            isContentSet = true;
        }

        revalidate();
        repaint();
    }

    // END: Expression listener methods

    private void emptyExpressionHolder() {
        selectLabel.setVisible(true);
        setOpaque(true);
        drawBorder = true;
        editStateOff();
    }

    private void populatedExpressionHolder() {
        selectLabel.setVisible(false);
        setOpaque(false);
        drawBorder = false;
    }

    public JComponent getExpression() {
        return expression;
    }

    public void setExpression(JComponent exp) {
        currentModelID = ((IDomainObjectUI) exp).getModelID();
        ;
        if (expression != null)
            remove(expression);
        this.expression = exp;
        populatedExpressionHolder();
        if (isEditing) {
            enableEdition();
        } else {
            disableEdition();
        }
        add(this.expression, 0);
        isContentSet = true;
        revalidate();
        repaint();
    }

    public String getCurrentModelID() {
        return currentModelID;
    }

    public void setCurrentModelID(String currentModelID) {
        this.currentModelID = currentModelID;
    }

    public String getOperationContext() {
        return operationContext;
    }

    public void setOperationContext(String operationContext) {
        this.operationContext = operationContext;
    }

    public void editStateOff() {
        operationsBtn.setVisible(false);
        revalidate();
        repaint();
    }

    public void editStateOn() {
        operationsBtn.setVisible(true);
        revalidate();
        repaint();
    }

    public void enableComparison() {
        isComparisonEnabled = true;
    }

    public void disableComparison() {
        isComparisonEnabled = false;
    }

    public void enableEdition() {
        isEditing = true;
        if (expression != null) {
            if (expression instanceof VariableSelectorUI) {
                ((VariableSelectorUI) expression).editStateOn();
            } else if (expression instanceof OperationUI) {
                ((OperationUI) expression).enableEdition();
                editStateOn();
            } else if (expression instanceof ConstantUI) {
                ((ConstantUI) expression).editStateOn();
            }
        }
    }

    public void disableEdition() {
        isEditing = false;
        if (expression != null) {
            if (expression instanceof VariableSelectorUI) {
                String item = ((VariableSelectorUI) expression).getVarListSelectedItem();
                ((VariableSelectorUI) expression).editStateOff(item);
            } else if (expression instanceof OperationUI) {
                ((OperationUI) expression).disableEdition();
                editStateOff();
            } else if (expression instanceof ConstantUI) {
                ((ConstantUI) expression).editStateOff("");
            }
        }
    }

    public boolean isComparison() {
        return isComparison;
    }

    public void setComparison(boolean isComparison) {
        this.isComparison = isComparison;
    }

    public void warningStateOn() {
        if (Services.getService().getViewMapping().get(parentModelID) instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
            ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (getParent() instanceof ExpressionFieldUI) {
            ((ExpressionFieldUI) getParent()).setEdition(true);
            enableEdition();
        }
    }

}
