package usp.ime.line.ivprog.view.domaingui.variables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPVariablePanel extends JPanel implements IVariableListener {
    private static final long serialVersionUID = -2214975678822644250L;
    private JPanel            container;
    private JButton           addVarBtn;
    private RoundedJPanel     varPanel;
    private JButton           addParamBtn;
    private RoundedJPanel     paramPanel;
    private String            scopeID;
    private Vector            variableList;
    private Vector            paramList;
    private TreeMap           varMap;
    private TreeMap           paramMap;
    
    public IVPVariablePanel(String scopeID, boolean isMain) {
        setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
        this.scopeID = scopeID;
        initialization(isMain);
        Services.getService().getController().getProgram().addVariableListener(this);
    }
    
    private void initialization(boolean isMain) {
        initVectors();
        initLayout();
        initContainer();
        if (!isMain) {
            initParamBtn();
            initParamPanel();
        }
        initAddVarBtn();
        initVarPanel();
    }
    
    private void initVectors() {
        variableList = new Vector();
        paramList = new Vector();
        varMap = new TreeMap();
        paramMap = new TreeMap();
    }
    
    private void initLayout() {
        setBackground(FlatUIColors.MAIN_BG);
        setLayout(new BorderLayout(0, 0));
    }
    
    private void initParamPanel() {
        paramPanel = new RoundedJPanel();
        paramPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, paramPanel, paramPanel.getClass(), 1));
        GridBagConstraints gbc_paramPanel = new GridBagConstraints();
        gbc_paramPanel.insets = new Insets(2, 0, 2, 0);
        gbc_paramPanel.fill = GridBagConstraints.BOTH;
        gbc_paramPanel.gridx = 1;
        gbc_paramPanel.gridy = 1;
        container.add(paramPanel, gbc_paramPanel);
    }
    
    private void initParamBtn() {
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().addParameter(scopeID);
                Tracking.getInstance().track("event=CLICK;where=BTN_NEWPARAMETER;");
            }
        };
        action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_param.png")));
        action.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPVariablePanel.action.addParam") + "Principal");
        addParamBtn = new JButton(action);
        addParamBtn.setHorizontalAlignment(SwingConstants.LEFT);
        addParamBtn.setUI(new IconButtonUI());
        addParamBtn.setPreferredSize(new Dimension(95, 25));
        GridBagConstraints gbc_addParamBtn = new GridBagConstraints();
        gbc_addParamBtn.anchor = GridBagConstraints.WEST;
        gbc_addParamBtn.insets = new Insets(3, 3, 3, 3);
        gbc_addParamBtn.gridx = 0;
        gbc_addParamBtn.gridy = 1;
        container.add(addParamBtn, gbc_addParamBtn);
    }
    
    private void initVarPanel() {
        varPanel = new RoundedJPanel();
        varPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, varPanel, varPanel.getClass(), 1));
        GridBagConstraints gbc_varPanel = new GridBagConstraints();
        gbc_varPanel.insets = new Insets(5, 5, 5, 5);
        gbc_varPanel.fill = GridBagConstraints.BOTH;
        gbc_varPanel.gridx = 1;
        gbc_varPanel.gridy = 0;
        container.add(varPanel, gbc_varPanel);
    }
    
    private void initAddVarBtn() {
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Services.getService().getController().addVariable(scopeID, "1");
                Tracking.getInstance().track("event=CLICK;where=BTN_NEWVARIABLE;");
            }
        };
        action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_var.png")));
        action.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPVariablePanel.action.addVar") + "Principal");
        addVarBtn = new JButton(action);
        addVarBtn.setUI(new IconButtonUI());
        GridBagConstraints gbc_addVarBtn = new GridBagConstraints();
        gbc_addVarBtn.anchor = GridBagConstraints.NORTHWEST;
        gbc_addVarBtn.insets = new Insets(3, 3, 3, 3);
        gbc_addVarBtn.gridy = 0;
        container.add(addVarBtn, gbc_addVarBtn);
    }
    
    private void initContainer() {
        container = new JPanel();
        container.setBorder(new EmptyBorder(0, 0, 5, 0));
        container.setOpaque(false);
        add(container, BorderLayout.CENTER);
        GridBagLayout gbl_container = new GridBagLayout();
        gbl_container.columnWidths = new int[] { 0, 0, 0 };
        gbl_container.rowHeights = new int[] { 0, 0, 0 };
        gbl_container.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_container.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        container.setLayout(gbl_container);
    }
    
    public void repaintVarPanel() {
        varPanel.removeAll();
        Object[] keySetArray = varMap.keySet().toArray();
        for (int i = 0; i < keySetArray.length; i++) {
            Component variableUI = (Component) varMap.get(keySetArray[i]);
            if (variableUI != null)
                varPanel.add(variableUI);
        }
        varPanel.revalidate();
        varPanel.repaint();
    }
    
    public void repaintParamPanel() {
        paramPanel.removeAll();
        String[] keySetArray = (String[]) paramMap.keySet().toArray();
        for (int i = 0; i < keySetArray.length; i++) {
            Component variableUI = (Component) paramMap.get(keySetArray[i]);
            if (variableUI != null)
                paramPanel.add(variableUI);
        }
        paramPanel.revalidate();
        paramPanel.repaint();
    }
    
    public void addedVariable(String id) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (!var.getVariableName().contains("#@ivprog@#!")) {
            IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getRenderer().paint(id);
            varMap.put(id, variable);
            repaintVarPanel();
        }
    }
    
    public void changeVariable(String id) {
    }
    
    public void removedVariable(String id) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (var != null) {
            if (!var.getVariableName().contains("#@ivprog@#!")) {
                varMap.put(id, null);
                repaintVarPanel();
            }
        }
    }
    
    public void changeVariableName(String id, String name, String lastName) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (!var.getVariableName().contains("#@ivprog@#!")) {
            IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
            if (variable != null) {
                variable.setVariableName(name);
            }
        }
    }
    
    public void changeVariableValue(String id, String value) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (!var.getVariableName().contains("#@ivprog@#!")) {
            IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
            if (variable != null) {
                variable.setVariableValue(value);
            }
        }
    }
    
    public void changeVariableType(String id, short type) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (!var.getVariableName().contains("#@ivprog@#!")) {
            IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
            if (variable != null) {
                variable.setVariableType(type);
            }
        }
    }
    
    public void variableRestored(String id) {
        Variable var = (Variable) Services.getService().getModelMapping().get(id);
        if (!var.getVariableName().contains("#@ivprog@#!")) {
            IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
            varMap.put(id, variable);
            repaintVarPanel();
        }
    }
    
    public void updateReference(String id) {
    }
}
