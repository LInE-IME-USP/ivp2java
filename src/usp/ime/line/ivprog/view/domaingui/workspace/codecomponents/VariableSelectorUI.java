package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.model.domainaction.ExpressionTypeChanged;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class VariableSelectorUI extends JPanel implements IVariableListener, IDomainObjectUI {
    public static final Color borderColor      = new Color(230, 126, 34);
    public static final Color hoverColor       = FlatUIColors.HOVER_COLOR;
    private String            currentModelID;
    private String            parentModelID;
    private String            scopeModelID;
    private String            referencedID;
    private String            context;
    private String            lastRemoved      = "";
    private JComboBox         varList;
    private TreeMap           indexMap;
    private JLabel            nameLabel;
    private JLabel            icon;
    private boolean           isUpdate         = true;
    private boolean           warningState     = false;
    private boolean           isOnlyOneElement = false;
    private boolean           isIsolated       = false;
    private JLabel            iconLabel;
    private boolean           drawBorder       = true;
    private boolean           editState        = true;
    private short             referencedType   = -1;
    
    public VariableSelectorUI(String parent) {
        this.parentModelID = parent;
        initialization();
        initComponents();
        Services.getService().getController().getProgram().addVariableListener(this);
    }
    
    // BEGIN: initialization methods
    private void initialization() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(3);
        flowLayout.setHgap(3);
        setLayout(flowLayout);
        addMouseListener(new ExpressionMouseListener(this));
    }
    
    private void initComponents() {
        initVector();
        initLabel();
        initConfigMenu();
        initIconLabel();
    }
    
    private void initIconLabel() {
        iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(VariableSelectorUI.class.getResource("/usp/ime/line/resources/icons/attention.png")));
        add(iconLabel);
        iconLabel.setVisible(false);
    }
    
    private void initVector() {
        indexMap = new TreeMap();
    }
    
    private void initLabel() {
        nameLabel = new JLabel(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
        nameLabel.setForeground(FlatUIColors.CHANGEABLE_ITEMS_COLOR);
        add(nameLabel);
    }
    
    private void initConfigMenu() {
        varList = new JComboBox();
        varList.setVisible(false);
        initValues();
        varList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!isUpdate) {
                    JComboBox cb = (JComboBox) evt.getSource();
                    Object item = cb.getSelectedItem();
                    if (evt.getActionCommand().equals("comboBoxChanged")) {
                        if (isIsolated) {
                            editStateOff((String) item);
                        }
                        String newRefID = getNewVarID();
                        if (newRefID != null) {
                            Services.getService().getController().updateVariableReference(currentModelID, newRefID);
                        }
                        if (warningState) {
                            turnWaningStateOFF();
                        }
                    }
                }
            }
        });
        add(varList);
    }
    
    private String getNewVarID() {
        Function f = (Function) Services.getService().getModelMapping().get(scopeModelID);
        Vector variables = f.getLocalVariableMap().toVector();
        String item = (String) varList.getSelectedItem();
        for (int i = 0; i < variables.size(); i++) {
            Variable var = (Variable) Services.getService().getModelMapping().get(variables.get(i));
            if (var.getVariableName().equals(item)) {
                return var.getUniqueID();
            }
        }
        return null;
    }
    
    private void initValues() {
        String parentID = parentModelID;
        if (parentID.contains("_"))
            parentID = parentModelID.substring(0, parentModelID.indexOf("_"));
        DataObject component = (DataObject) Services.getService().getModelMapping().get(parentID);
        Function f = (Function) Services.getService().getModelMapping().get(component.getScopeID());
        Vector variables = f.getLocalVariableMap().toVector();
        for (int i = 0; i < variables.size(); i++) {
            Variable var = (Variable) Services.getService().getModelMapping().get(variables.get(i));
            String name = (var).getVariableName();
            indexMap.put(var.getUniqueID(), name);
        }
        isUpdate = true;
        updateVariableList("", "");
        isUpdate = false;
    }
    
    private void updateValuesFromVariableList() {
        System.out.println("VariableSelectorUI.updateValuesFromVariableList " + referencedType);
        if (!isIsolated && (referencedType != -1 && referencedType != 0)) {
            System.out.println("VariableSelectorUI.updateValuesFromVariableList : entrou");
            Function f = (Function) Services.getService().getModelMapping().get(getScopeID());
            Vector variables = f.getLocalVariableMap().toVector();
            indexMap = new TreeMap();
            for (int i = 0; i < variables.size(); i++) {
                Variable var = (Variable) Services.getService().getModelMapping().get(variables.get(i));
                if (referencedType == var.getVariableType()) {
                    indexMap.put(var.getUniqueID(), var.getVariableName());
                } else if (referencedType == Expression.EXPRESSION_DOUBLE && var.getVariableType() == Expression.EXPRESSION_INTEGER) {
                    indexMap.put(var.getUniqueID(), var.getVariableName());
                }
            }
            isUpdate = true;
            updateVariableList("", "");
            isUpdate = false;
        }
    }
    
    // END: initialization methods
    // BEGIN: Variable listener methods
    public void addedVariable(String id) {
        Variable v = ((Variable) Services.getService().getModelMapping().get(id));
        String name = "";
        if (isIsolated) {
            name = v.getVariableName();
            indexMap.put(id, name);
            isUpdate = true;
            updateVariableList("", "");
            isUpdate = false;
        } else {
            System.out.println("VariableSelectorUI.addVariable " + v);
            System.out.println("VariableSelectorUI.addVariable " + v.getVariableType());
            System.out.println("VariableSelectorUI.addVariable " + referencedType);
            if (v.getVariableType() == referencedType) {
                name = v.getVariableName();
                indexMap.put(id, name);
                isUpdate = true;
                updateVariableList("", "");
                isUpdate = false;
            } else if (referencedType == -1 || referencedType == 0) {
                System.out.println("VariableSelectorUI.addVariable " + "");
                name = v.getVariableName();
                indexMap.put(id, name);
                isUpdate = true;
                updateVariableList("", "");
                isUpdate = false;
            }
        }
    }
    
    public void changeVariable(String id) {
    }
    
    public void removedVariable(String id) {
        String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
        if (indexMap.containsKey(id)) {
            indexMap.put(id, null);
            if (isIsolated) {
                if (nameLabel.isVisible()) {
                    if (nameLabel.getText().equals(name)) {
                        lastRemoved = name;
                        turnWaningStateON();
                    }
                }
            } else {
                if (name.equals(varList.getSelectedItem())) {
                    if (isEditState()) {
                        lastRemoved = name;
                        turnWaningStateON();
                    } else {
                        if (Services.getService().getModelMapping().get(parentModelID) instanceof ExpressionHolderUI) {
                            ((ExpressionHolderUI) Services.getService().getModelMapping().get(parentModelID)).warningStateOn();
                        }
                        lastRemoved = name;
                        turnWaningStateON();
                    }
                }
            }
            isUpdate = true;
            updateVariableList("", "");
            isUpdate = false;
        }
    }
    
    public void changeVariableName(String id, String name, String lastName) {
        if (indexMap.containsKey(id)) {
            isUpdate = true;
            indexMap.put(id, name);
            updateVariableList(name, lastName);
            isUpdate = false;
            if (nameLabel.isVisible() && nameLabel.getText().equals(lastName)) {
                nameLabel.setText(name);
                nameLabel.revalidate();
                nameLabel.repaint();
            }
        }
    }
    
    public void updateReference(String id) {
        if (id == currentModelID) {
            String name = ((VariableReference) Services.getService().getModelMapping().get(id)).getReferencedName();
            isUpdate = true;
            varList.setSelectedItem(name);
            isUpdate = false;
            if (referencedType == 0 || referencedType == -1) {
                referencedType = ((Variable) Services.getService().getModelMapping().get(getNewVarID())).getVariableType();
                updateValuesFromVariableList();
                isUpdate = true;
                updateVariableList("", "");
                isUpdate = false;
            }
            if (isIsolated) {
                editStateOff(name);
                if (Services.getService().getViewMapping().get(parentModelID) instanceof AttributionLineUI) {
                    if ("".equals(name) || name == null) {
                        ((AttributionLineUI) Services.getService().getViewMapping().get(parentModelID)).setLeftVarSet(false);
                        drawBorder = true;
                        setBackground(FlatUIColors.MAIN_BG);
                    } else {
                        drawBorder = false;
                        setBackground(FlatUIColors.CODE_BG);
                        ((AttributionLineUI) Services.getService().getViewMapping().get(parentModelID)).setLeftVarSet(true);
                        referencedType = ((Variable) Services.getService().getModelMapping().get(getNewVarID())).getVariableType();
                    }
                }
            } else {
                if (nameLabel.isVisible() && !("".equals(name) || name == null)) {
                    nameLabel.setText(name);
                    nameLabel.revalidate();
                    nameLabel.repaint();
                } else {
                    nameLabel.setText(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
                    nameLabel.revalidate();
                    nameLabel.repaint();
                }
                if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
                    if (getNewVarID() != null && !"".equals(getNewVarID())) {
                        ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).setExpressionType(((Variable) Services.getService().getModelMapping().get(getNewVarID()))
                                .getVariableType());
                    } else {
                        if (Services.getService().getViewMapping().get(parentModelID) instanceof BooleanOperationUI) {
                            if (!((BooleanOperationUI) Services.getService().getViewMapping().get(parentModelID)).isBothContentSet()) {
                                ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).setExpressionType((short) -1);
                                System.out.println("CHEGOU AQUI... DEVERIA FUNCIONAR... ");
                                referencedType = -1;
                                initValues();
                                isUpdate = true;
                                updateVariableList("", "");
                                isUpdate = false;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void changeVariableType(String id, short type) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        if (isIsolated) {
            if (v.getVariableName().equals(nameLabel.getText()) && nameLabel.isVisible()) {
                referencedType = type;
            }
        } else {
            System.out.println("VariableSelectorUI.changeVariableType ");
            if (indexMap.containsValue(v.getVariableName())) {
                System.out.println("VariableSelectorUI.changeVariableType : está no indexMap");
                if (v.getVariableName().equals(nameLabel.getText()) && nameLabel.isVisible() || !nameLabel.isVisible() && v.getVariableName().equals(varList.getSelectedItem())) {
                    lastRemoved = v.getVariableName();
                    updateValuesFromVariableList();
                    turnWaningStateON();
                    System.out.println("VariableSelectorUI.changeVariableType : está no indexMap e está visível");
                } else {
                    System.out.println("VariableSelectorUI.changeVariableType : está no indexMap não está visível, então ficou doido");
                    updateValuesFromVariableList();
                }
            } else {
                if (v.getVariableType() == referencedType) {
                    indexMap.put(v.getUniqueID(), v.getVariableName());
                    isUpdate = true;
                    updateVariableList("", "");
                    isUpdate = false;
                    if (v.getVariableName().equals(lastRemoved)) {
                        turnWaningStateOFF();
                        isUpdate = true;
                        varList.setSelectedItem(lastRemoved);
                        isUpdate = false;
                    }
                }
            }
        }
    }
    
    public void variableRestored(String id) {
        String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
        if (indexMap.containsKey(id)) {
            indexMap.put(id, name);
            isUpdate = true;
            updateVariableList("", "");
            isUpdate = false;
            if (isIsolated) {
                if (nameLabel.isVisible()) {
                    if (lastRemoved.equals(name)) {
                        nameLabel.setText(name);
                        turnWaningStateOFF();
                    }
                } else {
                    if (lastRemoved.equals(name)) {
                        turnWaningStateOFF();
                        isUpdate = true;
                        varList.setSelectedItem(lastRemoved);
                        isUpdate = false;
                    }
                }
            } else {
                if (lastRemoved.equals(name)) {
                    turnWaningStateOFF();
                    isUpdate = true;
                    varList.setSelectedItem(lastRemoved);
                    isUpdate = false;
                }
            }
            lastRemoved = "";
            revalidate();
            repaint();
        }
    }
    
    public void changeVariableValue(String id, String value) {
    }
    
    // END: Variable listener methods
    // BEGIN: support methods
    public void editStateOn() {
        varList.setVisible(true);
        nameLabel.setVisible(false);
        drawBorder = false;
        if (getParent() instanceof ExpressionHolderUI)
            ((ExpressionHolderUI) getParent()).editStateOn();
        if (!isIsolated)
            editState = true;
        revalidate();
        repaint();
    }
    
    public void editStateOff(String item) {
        varList.setVisible(false);
        if (item != null && item != "")
            nameLabel.setText(item);
        else
            nameLabel.setText(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
        nameLabel.setVisible(true);
        if (getParent() instanceof ExpressionHolderUI)
            ((ExpressionHolderUI) getParent()).editStateOff();
        if (!isIsolated)
            editState = false;
        revalidate();
        repaint();
    }
    
    private void turnWaningStateON() {
        varList.setBorder(BorderFactory.createLineBorder(Color.red));
        warningState = true;
        editStateOn();
        if (Services.getService().getViewMapping().get(parentModelID) instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
            ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else {
            editStateOn();
        }
    }
    
    private void turnWaningStateOFF() {
        varList.setBorder(null);
        warningState = false;
        if (Services.getService().getViewMapping().get(parentModelID) instanceof ExpressionHolderUI) {
            ((ExpressionHolderUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI) {
            ((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
        } else {
            editStateOn();
        }
    }
    
    private void updateVariableList(String newName, String lastName) {
        Object itemSelected = varList.getSelectedItem();
        varList.removeAllItems();
        Object[] keySetArray = indexMap.keySet().toArray();
        int count = 0;
        for (int i = 0; i < keySetArray.length; i++) {
            String variableName = (String) indexMap.get(keySetArray[i]);
            if (variableName != null) {
                count++;
            }
        }
        isOnlyOneElement = count == 1 ? true : false;
        for (int i = 0; i < keySetArray.length; i++) {
            String variableName = (String) indexMap.get(keySetArray[i]);
            if (variableName != null) {
                varList.addItem(variableName);
            }
        }
        if (lastName.equals(itemSelected)) {
            varList.setSelectedItem(newName);
        } else {
            varList.setSelectedItem(itemSelected);
        }
    }
    
    public String getScopeID() {
        return scopeModelID;
    }
    
    public void setScopeID(String scopeID) {
        this.scopeModelID = scopeID;
    }
    
    public String getCurrentModelID() {
        return currentModelID;
    }
    
    public void setCurrentModelID(String currentModelID) {
        this.currentModelID = currentModelID;
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
    
    public boolean isEditState() {
        return editState;
    }
    
    public void setEditState(boolean editState) {
        this.editState = editState;
    }
    
    public String getVarListSelectedItem() {
        return (String) varList.getSelectedItem();
    }
    
    public void setIsolationMode(boolean isIso) {
        isIsolated = isIso;
        setBackground(FlatUIColors.MAIN_BG);
    }
    
    public boolean isIsolated() {
        return isIsolated;
    }
    
    public short referenceType() {
        return ((Variable) Services.getService().getModelMapping().get(getNewVarID())).getVariableType();
    }
    
    public short getReferencedType() {
        return referencedType;
    }
    
    public void setReferencedType(short referencedType) {
        this.referencedType = referencedType;
        updateValuesFromVariableList();
        isUpdate = true;
        updateVariableList("", "");
        isUpdate = false;
    }
    
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
        private int    clickCounter = 0;
        
        public ExpressionMouseListener(JPanel c) {
            container = c;
        }
        
        public void mouseEntered(MouseEvent e) {
            if (editState || isIsolated) {
                setBackground(hoverColor);
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        
        public void mouseExited(MouseEvent e) {
            if (editState || isIsolated) {
                if (drawBorder) {
                    setBackground(FlatUIColors.MAIN_BG);
                } else {
                    setBackground(FlatUIColors.CODE_BG);
                }
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        }
        
        public void mouseClicked(MouseEvent arg0) {
            if (editState) {
                editStateOn();
                varList.requestFocus();
            }
        }
        
        public void mousePressed(MouseEvent arg0) {
        }
        
        public void mouseReleased(MouseEvent arg0) {
        }
    }
    // END: Mouse listener
}
