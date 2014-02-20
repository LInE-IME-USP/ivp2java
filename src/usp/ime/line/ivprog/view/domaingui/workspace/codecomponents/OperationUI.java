package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IOperationListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.view.FlatUIColors;

public abstract class OperationUI extends JPanel implements IDomainObjectUI, IOperationListener {
    private JLabel               leftPar;
    protected ExpressionHolderUI expressionBaseUI_1;
    protected JLabel             expSign;
    protected ExpressionHolderUI expressionBaseUI_2;
    private JLabel               rightPar;
    protected String             currentModelID;
    protected String             parentModelID;
    protected String             scopeModelID;
    protected String             context;
    private JPanel               expPanel;
    private boolean              drawBorder = false;
    private boolean              isEditing  = false;
    protected JPopupMenu         operationSignMenu;
    protected short              expressionType;
    
    public OperationUI(String parent, String scope, String id) {
        currentModelID = id;
        scopeModelID = scope;
        parentModelID = parent;
        context = "";
        setOpaque(false);
        initLayout();
        initComponents();
        initOperationSignMenu();
        initSignal();
        Services.getService().getController().getProgram().addOperationListener(this);
    }
    
    public abstract void initOperationSignMenu();
    
    public abstract void initSignal();
    
    protected void initComponents() {
        initLeftParenthesis();
        initExpressionHolder1();
        initExpressionSign();
        initExpressionHolder2();
        initRightParenthesis();
    }
    
    private void initRightParenthesis() {
        rightPar = new JLabel(")");
        add(rightPar);
    }
    
    private void initExpressionHolder2() {
        expressionBaseUI_2 = new ExpressionHolderUI(currentModelID, scopeModelID);
        expressionBaseUI_2.setOperationContext("right");
        if (isEditing) {
            expressionBaseUI_2.enableEdition();
        } else {
            expressionBaseUI_2.disableEdition();
        }
        add(expressionBaseUI_2);
    }
    
    private void initExpressionSign() {
        expPanel = new JPanel();
        expPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        expPanel.addMouseListener(new OperationMouseListener(expPanel));
        add(expPanel);
        expSign = new JLabel();
        expSign.setForeground(FlatUIColors.CHANGEABLE_ITEMS_COLOR);
        expPanel.add(expSign);
    }
    
    private void initExpressionHolder1() {
        expressionBaseUI_1 = new ExpressionHolderUI(currentModelID, scopeModelID);
        expressionBaseUI_1.setOperationContext("left");
        if (isEditing) {
            expressionBaseUI_1.enableEdition();
        } else {
            expressionBaseUI_1.disableEdition();
        }
        add(expressionBaseUI_1);
    }
    
    private void initLeftParenthesis() {
        leftPar = new JLabel("(");
        add(leftPar);
    }
    
    protected void initLayout() {
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setHgap(3);
        flowLayout.setVgap(0);
        flowLayout.setAlignment(FlowLayout.LEFT);
    }
    
    public ExpressionHolderUI getExpressionBaseUI_1() {
        return expressionBaseUI_1;
    }
    
    public void setExpressionBaseUI_1(JComponent expressionBaseUI_1) {
        this.expressionBaseUI_1.setExpression(expressionBaseUI_1);
        revalidate();
        repaint();
    }
    
    public ExpressionHolderUI getExpressionBaseUI_2() {
        return expressionBaseUI_2;
    }
    
    public void setExpressionBaseUI_2(JComponent expressionBaseUI_2) {
        this.expressionBaseUI_2.setExpression(expressionBaseUI_2);
        revalidate();
        repaint();
    }
    
    public String getModelID() {
        return currentModelID;
    }
    
    public String getModelParent() {
        return parentModelID;
    }
    
    public String getModelScope() {
        return scopeModelID;
    }
    
    public void setModelID(String id) {
        currentModelID = id;
        initSignal();
    }
    
    public void setModelParent(String id) {
        parentModelID = id;
    }
    
    public void setModelScope(String id) {
        scopeModelID = id;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getContext() {
        return context;
    }
    
    class OperationMouseListener implements MouseListener {
        private JPanel panel;
        
        public OperationMouseListener(JPanel p) {
            panel = p;
        }
        
        public void mouseClicked(MouseEvent arg0) {
            if (isEditing) {
                operationSignMenu.show(panel, 0, panel.getHeight());
                operationSignMenu.requestFocus();
            }
        }
        
        public void mouseEntered(MouseEvent e) {
            if (isEditing) {
                panel.setBackground(FlatUIColors.HOVER_COLOR);
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        
        public void mouseExited(MouseEvent e) {
            if (isEditing) {
                panel.setBackground(FlatUIColors.CODE_BG);
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        }
        
        public void mousePressed(MouseEvent arg0) {
        }
        
        public void mouseReleased(MouseEvent arg0) {
        }
    }
    
    public void enableEdition() {
        isEditing = true;
        expressionBaseUI_1.enableEdition();
        expressionBaseUI_2.enableEdition();
    }
    
    public void disableEdition() {
        isEditing = false;
        expressionBaseUI_1.disableEdition();
        expressionBaseUI_2.disableEdition();
    }
    
    public void warningStateOn() {
        if (Services.getService().getViewMapping().get(parentModelID) instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
            ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (getParent() instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) getParent()).warningStateOn();
        } else {
            enableEdition();
        }
    }
    
    public short getExpressionType() {
        return expressionType;
    }
    
    public void setExpressionType(short expressionType) {
        this.expressionType = expressionType;
        expressionBaseUI_1.setHoldingType(expressionType);
        expressionBaseUI_2.setHoldingType(expressionType);
    }
    
    public boolean isBothContentSet() {
        System.out.println("MODEL CONTENT SET > " + Services.getService().getModelMapping().get(expressionBaseUI_1.getCurrentModelID()));
        String ref1 = null, ref2 = null;
        if (Services.getService().getModelMapping().get(expressionBaseUI_1.getCurrentModelID()) instanceof VariableReference) {
            ref1 = ((VariableReference) Services.getService().getModelMapping().get(expressionBaseUI_1.getCurrentModelID())).getReferencedVariable();
            System.out.println("isBothContentSet 1 " + ref1);
        }
        if (Services.getService().getModelMapping().get(expressionBaseUI_2.getCurrentModelID()) instanceof VariableReference) {
            ref2 = ((VariableReference) Services.getService().getModelMapping().get(expressionBaseUI_2.getCurrentModelID())).getReferencedVariable();
            System.out.println("isBothContentSet 2 " + ref2);
        }
        System.out.println("result " + (ref1 != null || ref2 != null));
        return (ref1 != null || ref2 != null);
    }
}