package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Constant;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditBoolean;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditInPlace;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.FlowLayout;

public class ConstantUI extends JPanel implements IDomainObjectUI, IValueListener {
    private String      currentModelID;
    private String      parentModelID;
    private String      scopeModelID;
    private String      context;
    private short       expressionType;
    private EditBoolean editBool;
    private EditInPlace editInPlace;
    private boolean     isEditing = false;
    private JLabel      valueLabel;
    
    public ConstantUI(String modelID) {
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setVgap(2);
        flowLayout.setHgap(2);
        currentModelID = modelID;
        initBool();
        initEditInPlace();
        valueLabel = new JLabel();
        valueLabel.setForeground(Color.BLUE);
        add(valueLabel);
        add(editBool);
        add(editInPlace);
        setOpaque(false);
    }
    
    private void initEditInPlace() {
        editInPlace = new EditInPlace(FlatUIColors.CODE_BG);
        editInPlace.setValueListener(new IValueListener() {
            public void valueChanged(String value) {
                Services.getService().getController().changeValue(currentModelID, value);
            }
        });
    }
    
    private void initBool() {
        editBool = new EditBoolean(FlatUIColors.CODE_BG);
        editBool.setValueListener(new IValueListener() {
            public void valueChanged(String value) {
                Services.getService().getController().changeValue(currentModelID, value);
            }
        });
    }
    
    private void changeVariableType(short type) {
        Constant c = (Constant) Services.getService().getModelMapping().get(currentModelID);
        if (type == Expression.EXPRESSION_INTEGER) {
            editInPlace.setCurrentPattern(EditInPlace.PATTERN_VARIABLE_VALUE_INTEGER);
            editInPlace.setValue(c.getConstantValue());
        } else if (type == Expression.EXPRESSION_DOUBLE) {
            editInPlace.setCurrentPattern(EditInPlace.PATTERN_VARIABLE_VALUE_DOUBLE);
            editInPlace.setValue(c.getConstantValue());
        } else if (type == Expression.EXPRESSION_STRING) {
            editInPlace.setValue(c.getConstantValue());
        } else if (type == Expression.EXPRESSION_BOOLEAN) {
            editBool.setValue(c.getConstantValue());
        }
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
    
    public short getExpressionType() {
        return expressionType;
    }
    
    public void setExpressionType(short expressionType) {
        this.expressionType = expressionType;
        changeVariableType(expressionType);
    }
    
    private void warningStateOn() {
    }
    
    private void warningStateOff() {
    }
    
    public void editStateOn() {
        isEditing = true;
        valueLabel.setVisible(false);
        if (expressionType != Expression.EXPRESSION_BOOLEAN) {
            editInPlace.setVisible(true);
            editBool.setVisible(false);
        } else {
            editBool.setVisible(true);
            editInPlace.setVisible(false);
        }
        if (getParent() instanceof ExpressionHolderUI)
            ((ExpressionHolderUI) getParent()).editStateOn();
    }
    
    public void editStateOff(String string) {
        isEditing = false;
        valueLabel.setVisible(true);
        valueLabel.setText(((Constant) Services.getService().getModelMapping().get(currentModelID)).getConstantValue());
        editInPlace.setVisible(false);
        editBool.setVisible(false);
        if (getParent() instanceof ExpressionHolderUI)
            ((ExpressionHolderUI) getParent()).editStateOff();
    }
    
    public boolean isEditState() {
        return isEditing;
    }
    
    public void valueChanged(String value) {
        if (expressionType == Expression.EXPRESSION_BOOLEAN) {
            editBool.setValue(value);
            if (value.equals("true"))
                valueLabel.setText("Verdadeiro");
            else
                valueLabel.setText("Falso");
        } else {
            editInPlace.setValue(value);
            valueLabel.setText(value);
        }
    }
    
    public boolean isContentSet() {
        return true;
    }
}
