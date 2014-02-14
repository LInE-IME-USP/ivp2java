package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Font;

public class AttributionLineUI extends CodeBaseUI {
    
    private JPanel             contentPanel;
    private JLabel             codeLabel;
    private VariableSelectorUI varSelector;
    private ExpressionFieldUI  expression;
    private String             context;
    private boolean            isLeftVarSet = false;
    private JLabel             blockedLabel;
    private String             leftVarModelID;
    
    public AttributionLineUI(String id, String scope, String parent) {
        super(id);
        setModelID(id);
        setModelParent(parent);
        setModelScope(scope);
        initialization();
        addComponents();
    }
    
    private void initialization() {
        expression = new ExpressionFieldUI(getModelID(), getModelScope());
        contentPanel = new JPanel();
        codeLabel = new JLabel(ResourceBundleIVP.getString("AttLine.text"));
        varSelector = new VariableSelectorUI(getModelID());
        varSelector.setModelScope(getModelScope());
        varSelector.setIsolationMode(true);
        blockedLabel = new JLabel(ResourceBundleIVP.getString("AttributionLineUI.lblNewLabel.text")); //$NON-NLS-1$
        blockedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        blockedLabel.setForeground(Color.PINK);
        setBackground(FlatUIColors.MAIN_BG);
    }
    
    private void addComponents() {
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPanel.add(varSelector);
        contentPanel.add(codeLabel);
        contentPanel.add(expression);
        contentPanel.add(blockedLabel);
        blockContent();
        addContentPanel(contentPanel);
    }
    
    public void blockContent() {
        codeLabel.setVisible(false);
        expression.setVisible(false);
        blockedLabel.setVisible(true);
    }
    
    public void unblockContent() {
        codeLabel.setVisible(true);
        expression.setVisible(true);
        expression.setHoldingType(((VariableReference) Services.getService().getModelMapping().get(leftVarModelID)).getReferencedType());
        blockedLabel.setVisible(false);
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setLeftVarModelID(String leftVariableID) {
        this.leftVarModelID = leftVariableID;
        varSelector.setModelID(leftVariableID);
    }
    
    public boolean isLeftVarSet() {
        return isLeftVarSet;
    }
    
    public void setLeftVarSet(boolean isLeftVarSet) {
        this.isLeftVarSet = isLeftVarSet;
        expression.setBlocked(!isLeftVarSet);
        if (isLeftVarSet) {
            unblockContent();
        } else {
            blockContent();
        }
    }
}
