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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
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
    private JPopupMenu        contentMenuWithValue;
    private JPopupMenu        contentMenuWithoutValue;
    private JPopupMenu        numberMenu;
    private JPopupMenu        stringMenu;
    private JPopupMenu        booleanMenu;
    private JPopupMenu        comparisonMenu;
    private JLabel            selectLabel;
    private String            parentModelID;
    private String            scopeModelID;
    private String            currentModelID;
    private String            operationContext;
    private JButton           operationsBtn;
    private boolean           hideOperationsBtn;
    private JComponent        expression;
    private short             holdingType         = -1;
    private boolean           isForHeader         = false;
    private String            forContext          = "";
    private boolean           warningState        = false;
    
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
        numberMenu = new JPopupMenu();
        stringMenu = new JPopupMenu();
        booleanMenu = new JPopupMenu();
        comparisonMenu = new JPopupMenu();
        addBooleanOperators(booleanMenu);
        addArithmeticOperations(numberMenu);
        addStringOperations(stringMenu);
        addComparison(comparisonMenu);
        addCleanContentForCondition(comparisonMenu);
        addCleanContent(booleanMenu);
        addCleanContent(numberMenu);
        addCleanContent(stringMenu);
    }
    
    private void addStringOperations(JPopupMenu menu) {
        Action changeToConcatenation = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_CONCAT, (short) -1, operationContext);
            }
        };
        changeToConcatenation.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.stringConcat.tip"));
        changeToConcatenation.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.stringConcat.text"));
        menu.add(changeToConcatenation);
        menu.addSeparator();
    }
    
    private void addCleanContentForCondition(JPopupMenu menu) {
        Action cleanContent = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().deleteExpression(expressionID, parentModelID, operationContext, true, true);
            }
        };
        cleanContent.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.cleanContent.tip"));
        cleanContent.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.cleanContent.text"));
        menu.add(cleanContent);
    }
    
    private void addBooleanOperators(JPopupMenu menu) {
        Action changeToAND = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_AND, (short) -1, operationContext);
            }
        };
        changeToAND.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.AND.tip"));
        changeToAND.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.AND.text"));
        Action changeToOR = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_OR, (short) -1, operationContext);
            }
        };
        changeToOR.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.OR.tip"));
        changeToOR.putValue(Action.NAME, ResourceBundleIVP.getString("BooleanOperationUI.OR.text"));
        menu.add(changeToAND);
        menu.add(changeToOR);
        menu.addSeparator();
    }
    
    private void addCleanContent(JPopupMenu menu) {
        Action cleanContent = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().deleteExpression(expressionID, parentModelID, forContext, true, false);
                } else {
                    Services.getService().getController().deleteExpression(expressionID, parentModelID, operationContext, true, false);
                }
            }
        };
        cleanContent.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.cleanContent.tip"));
        cleanContent.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.cleanContent.text"));
        menu.add(cleanContent);
    }
    
    private void addArithmeticOperations(JPopupMenu menu) {
        Action createAddition = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_ADDITION, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_ADDITION, (short) -1, operationContext);
                }
            }
        };
        createAddition.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.createAddition.tip"));
        createAddition.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.createAddition.text"));
        Action createSubtraction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_SUBTRACTION, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_SUBTRACTION, (short) -1, operationContext);
                }
            }
        };
        createSubtraction.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.createSubtraction.tip"));
        createSubtraction.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.createSubtraction.text"));
        Action createMultiplication = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_MULTIPLICATION, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_MULTIPLICATION, (short) -1, operationContext);
                }
            }
        };
        createMultiplication.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.createMultiplication.tip"));
        createMultiplication.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.createMultiplication.text"));
        Action createDivision = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_DIVISION, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_DIVISION, (short) -1, operationContext);
                }
            }
        };
        createDivision.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.createDivision.tip"));
        createDivision.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.createDivision.text"));
        Action createIntDiv = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                if (isForHeader) {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_INTDIV, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_INTDIV, (short) -1, operationContext);
                }
            }
        };
        createIntDiv.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.createIntDiv.tip"));
        createIntDiv.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.createIntDiv.text"));
        menu.add(createAddition);
        menu.add(createSubtraction);
        menu.add(createDivision);
        menu.add(createMultiplication);
        menu.addSeparator();
        menu.add(createIntDiv);
        menu.addSeparator();
    }
    
    private void addComparison(JPopupMenu menu) {
        Action changeToLEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_LEQ, (short) -1, operationContext);
            }
        };
        changeToLEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LEQ.tip"));
        changeToLEQ.putValue(Action.NAME, "\u2264");
        Action changeToLES = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_LES, (short) -1, operationContext);
            }
        };
        changeToLES.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.LES.tip"));
        changeToLES.putValue(Action.NAME, "\u003C");
        Action changeToEQU = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_EQU, (short) -1, operationContext);
            }
        };
        changeToEQU.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.EQU.tip"));
        changeToEQU.putValue(Action.NAME, "=");
        Action changeToNEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_NEQ, (short) -1, operationContext);
            }
        };
        changeToNEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.NEQ.tip"));
        changeToNEQ.putValue(Action.NAME, "\u2260");
        Action changeToGEQ = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_GEQ, (short) -1, operationContext);
            }
        };
        changeToGEQ.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("BooleanOperationUI.GEQ.tip"));
        changeToGEQ.putValue(Action.NAME, "\u2265");
        Action changeToGRE = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String expressionID = ((IDomainObjectUI) expression).getModelID();
                Services.getService().getController().createExpression(expressionID, parentModelID, Expression.EXPRESSION_OPERATION_GRE, (short) -1, operationContext);
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
        contentMenuWithValue = new JPopupMenu();
        Action variableHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isForHeader) {
                    Services.getService().getController().createExpression("", parentModelID, Expression.EXPRESSION_VARIABLE, holdingType, forContext);
                } else {
                    Services.getService().getController().createExpression("", parentModelID, Expression.EXPRESSION_VARIABLE, holdingType, operationContext);
                }
            }
        };
        variableHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.variableHasBeenChosen.tip"));
        variableHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.variableHasBeenChosen.text"));
        Action valueHasBeenChosen = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isForHeader) {
                    Services.getService().getController().createExpression("", parentModelID, holdingType, (short) -1, forContext);
                } else {
                    Services.getService().getController().createExpression("", parentModelID, holdingType, (short) -1, operationContext);
                }
            }
        };
        valueHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.valueHasBeensChosen.tip"));
        valueHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.valueHasBeensChosen.text"));
        contentMenuWithValue.add(variableHasBeenChosen);
        contentMenuWithValue.add(valueHasBeenChosen);
        contentMenuWithoutValue = new JPopupMenu();
        valueHasBeenChosen.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("ExpressionHolderUI.action.valueHasBeensChosen.tip"));
        valueHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionHolderUI.action.valueHasBeensChosen.text"));
        contentMenuWithoutValue.add(variableHasBeenChosen);
    }
    
    private void initChangeContentBtn() {
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!isComparison) {
                    if (isComparisonEnabled) {
                        comparisonMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                    } else {
                        if (holdingType == Expression.EXPRESSION_DOUBLE || holdingType == Expression.EXPRESSION_INTEGER) {
                            numberMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                        } else if (holdingType == Expression.EXPRESSION_STRING) {
                            stringMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                        } else if (holdingType == Expression.EXPRESSION_BOOLEAN) {
                            booleanMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
                        }
                    }
                } else {
                    booleanMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
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
        selectLabel = new JLabel(ResourceBundleIVP.getString("ExpressionHolderUI.selectLabel.text"));
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
                if (holdingType != -1) {
                    contentMenuWithValue.show(container, 0, container.getHeight());
                    contentMenuWithValue.requestFocus();
                } else {
                    contentMenuWithoutValue.show(container, 0, container.getHeight());
                    contentMenuWithoutValue.requestFocus();
                }
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
        if (isForHeader) {
            expressionCreatedFor(holder, id, context);
        } else {
            expressionCreatedNormal(holder, id, context);
        }
    }
    
    private void expressionCreatedNormal(String holder, String id, String context) {
        if (holder.equals(parentModelID) && operationContext.equals(context)) {
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
                if (holdingType != -1) {
                    ((VariableSelectorUI) expression).setReferencedType(holdingType);
                }
            } else if (expression instanceof ConstantUI) {
                if (isEditing)
                    ((ConstantUI) expression).editStateOn();
                else {
                    ((ConstantUI) expression).editStateOff("");
                }
            } else {
                if (holdingType == -1) {
                    correctHoldingType(lastExp);
                }
                ((OperationUI) expression).setExpressionType(holdingType);
                if (!isComparison) {
                    if (lastExp != null && !"".equals(lastExp))
                        ((OperationUI) expression).setExpressionBaseUI_1(lastExp);
                }
                if (isEditing) {
                    ((OperationUI) expression).enableEdition();
                } else {
                    ((OperationUI) expression).disableEdition();
                }
                if (lastExp != null && !"".equals(lastExp)) {
                    ((IDomainObjectUI) lastExp).setModelParent(currentModelID);
                }
            }
            if (Services.getService().getViewMapping().get(parentModelID) instanceof CodeBaseUI) { // nessa atualização tenho que olhar o contexto...
                if (lastExp != null && !"".equals(lastExp)) {
                    Services.getService().getController().updateParent(parentModelID, ((IDomainObjectUI) lastExp).getModelID(), id, operationContext);
                } else {
                    Services.getService().getController().updateParent(parentModelID, "", id, operationContext);
                }
            }
            isContentSet = true;
            drawBorder = false;
            if (warningState)
                setBorder(null);
            revalidate();
            repaint();
        }
    }
    
    private void expressionCreatedFor(String holder, String id, String context) {
        if (holder.equals(parentModelID) && forContext.equals(context)) {
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
                if (holdingType != -1) {
                    ((VariableSelectorUI) expression).setReferencedType(holdingType);
                }
            } else if (expression instanceof ConstantUI) {
                if (isEditing)
                    ((ConstantUI) expression).editStateOn();
                else {
                    ((ConstantUI) expression).editStateOff("");
                }
            } else {
                if (holdingType == -1) {
                    correctHoldingType(lastExp);
                }
                ((OperationUI) expression).setExpressionType(holdingType);
                if (!isComparison) {
                    if (lastExp != null)
                        ((OperationUI) expression).setExpressionBaseUI_1(lastExp);
                }
                if (isEditing) {
                    ((OperationUI) expression).enableEdition();
                } else {
                    ((OperationUI) expression).disableEdition();
                }
                if (lastExp != null && !"".equals(lastExp)) {
                    ((IDomainObjectUI) lastExp).setModelParent(currentModelID);
                }
            }
            if (Services.getService().getViewMapping().get(parentModelID) instanceof CodeBaseUI) { // nessa atualização tenho que olhar o contexto...
                if (lastExp != null && !"".equals(lastExp)) {
                    Services.getService().getController().updateParent(parentModelID, ((IDomainObjectUI) lastExp).getModelID(), id, forContext);
                } else {
                    Services.getService().getController().updateParent(parentModelID, "", id, forContext);
                }
            }
            isContentSet = true;
            drawBorder = false;
            if (warningState)
                setBorder(null);
            revalidate();
            repaint();
        }
    }
    
    private void correctHoldingType(JComponent lastExp) {
        if (lastExp instanceof VariableSelectorUI) {
            holdingType = ((VariableSelectorUI) lastExp).referenceType();
        } else if (lastExp instanceof OperationUI) {
            holdingType = ((OperationUI) lastExp).getExpressionType();
        }
    }
    
    public void expressionDeleted(String id, String context, boolean isClean) {
        if (isForHeader) {
            expressionDeletedFor(id, context, isClean);
        } else {
            expressionDeletedNormal(id, context, isClean);
        }
    }
    
    private void expressionDeletedFor(String id, String context, boolean isClean) {
        if (expression != null) {
            if (currentModelID.equals(id) && forContext.equals(context)) {
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
    
    private void expressionDeletedNormal(String id, String context, boolean isClean) {
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
        if (isForHeader) {
            expressionRestoredFromCleaningFor(holder, id, context);
        } else {
            expressionRestoredFromCleaningNormal(holder, id, context);
        }
    }
    
    private void expressionRestoredFromCleaningFor(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && forContext.equals(context)) {
            Services.getService().getController().updateParent(parentModelID, currentModelID, id, forContext);
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            setExpression(restoredExp);
            isContentSet = true;
            drawBorder = false;
        }
        revalidate();
        repaint();
    }
    
    private void expressionRestoredFromCleaningNormal(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && operationContext.equals(context)) {
            Services.getService().getController().updateParent(parentModelID, currentModelID, id, operationContext);
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            setExpression(restoredExp);
            isContentSet = true;
        }
        revalidate();
        repaint();
    }
    
    public void expressionRestored(String holder, String id, String context) {
        if (isForHeader) {
            expressionRestoredFor(holder, id, context);
        } else {
            expressionRestoredNormal(holder, id, context);
        }
    }
    
    private void expressionRestoredFor(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && forContext.equals(context)) {
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            Services.getService().getController().updateParent(parentModelID, currentModelID, id, forContext);
            if (restoredExp instanceof OperationUI) {
                ((OperationUI) restoredExp).setExpressionBaseUI_1(expression);
            } else if (restoredExp instanceof ConstantUI) {
                if (((ConstantUI) restoredExp).isEditState()) {
                    editStateOn();
                } else {
                    editStateOff();
                }
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
    
    private void expressionRestoredNormal(String holder, String id, String context) {
        String lastExpID = null;
        if (holder.equals(parentModelID) && operationContext.equals(context)) {
            JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
            Services.getService().getController().updateParent(parentModelID, currentModelID, id, operationContext);
            if (restoredExp instanceof OperationUI) {
                ((OperationUI) restoredExp).setExpressionBaseUI_1(expression);
            } else if (restoredExp instanceof ConstantUI) {
                if (((ConstantUI) restoredExp).isEditState()) {
                    editStateOn();
                } else {
                    editStateOff();
                }
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
        if (!hideOperationsBtn) {
            operationsBtn.setVisible(false);
        }
        revalidate();
        repaint();
    }
    
    public void editStateOn() {
        if (!hideOperationsBtn) {
            operationsBtn.setVisible(true);
        }
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
        warningState = true;
        if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
            ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (getParent() instanceof ExpressionFieldUI) {
            ((ExpressionFieldUI) getParent()).setEdition(true);
        } else if (getParent() instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) getParent()).warningStateOn();
        }
        if (!isContentSet)
            setBorder(BorderFactory.createLineBorder(Color.red));
        revalidate();
        repaint();
    }
    
    public void warningStateOFF() {
        warningState = false;
        setBorder(null);
        revalidate();
        repaint();
    }
    
    public short getHoldingType() {
        return holdingType;
    }
    
    public void setHoldingType(short holdingType) {
        this.holdingType = holdingType;
    }
    
    public boolean isCSet() {
        boolean isCSet = true;
        if (isContentSet) {
            if (!((IDomainObjectUI) expression).isContentSet()) {
                isCSet = false;
            }
        } else {
            isCSet = false;
            warningStateOn();
        }
        return isCSet;
    }
    
    public boolean isContentSet() {
        return isContentSet;
    }
    
    public void setContentSet(boolean isContentSet) {
        this.isContentSet = isContentSet;
    }
    
    public void setHideMenu(boolean hideMenu) {
        operationsBtn.setVisible(!hideMenu);
        hideOperationsBtn = hideMenu;
    }
    
    public boolean isForHeader() {
        return isForHeader;
    }
    
    public void setForHeader(boolean isForHeader) {
        this.isForHeader = isForHeader;
    }
    
    public String getForContext() {
        return forContext;
    }
    
    public void setForContext(String forContext) {
        this.forContext = forContext;
    }
}
