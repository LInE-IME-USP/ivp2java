package usp.ime.line.ivprog.view.domaingui.variables;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import com.l2fprod.common.demo.TaskPaneMain;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.utils.IVPVariableMap;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.utils.BlueishButtonUI;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.SwingConstants;

public class IVPVariablePanel extends JPanel implements IVariableListener {

	private static final long serialVersionUID = -2214975678822644250L;
	private JPanel container;
	private JButton addVarBtn;
	private RoundedJPanel varPanel;
	private JButton addParamBtn;
	private RoundedJPanel paramPanel;
	private String scopeID;
	private Vector variableList;
	private Vector paramList;

	public IVPVariablePanel(String scopeID, boolean isMain) {
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
	}

	private void initLayout() {
		setBackground(new Color(220, 220, 255));
		setLayout(new BorderLayout(0, 0));
	}

	private void initParamPanel() {
		paramPanel = new RoundedJPanel();
		paramPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, paramPanel,
				paramPanel.getClass(), 1));
		GridBagConstraints gbc_paramPanel = new GridBagConstraints();
		gbc_paramPanel.insets = new Insets(2, 0, 2, 0);
		gbc_paramPanel.fill = GridBagConstraints.BOTH;
		gbc_paramPanel.gridx = 1;
		gbc_paramPanel.gridy = 1;
		container.add(paramPanel, gbc_paramPanel);
	}

	private void initParamBtn() {
		Action action = new AbstractAction(ResourceBundleIVP.getString("addParamBtn")) {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().addParameter(scopeID);
			}
		};
		action.putValue(Action.SMALL_ICON,new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_var.png")));
		action.putValue(Action.SHORT_DESCRIPTION,"Adiciona um novo parâmetro à função:" + "Principal");
		addParamBtn = new JButton(action);
		addParamBtn.setHorizontalAlignment(SwingConstants.LEFT);
		addParamBtn.setUI(new BlueishButtonUI());
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
		varPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, varPanel,
				varPanel.getClass(), 1));
		GridBagConstraints gbc_varPanel = new GridBagConstraints();
		gbc_varPanel.insets = new Insets(2, 0, 2, 0);
		gbc_varPanel.fill = GridBagConstraints.BOTH;
		gbc_varPanel.gridx = 1;
		gbc_varPanel.gridy = 0;
		container.add(varPanel, gbc_varPanel);
	}

	private void initAddVarBtn() {
		Action action = new AbstractAction(ResourceBundleIVP.getString("addVarBtn")) {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().addVariable(scopeID);
			}
		};
		action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_var.png")));
		action.putValue(Action.SHORT_DESCRIPTION, "Adiciona um novo parâmetro à função:" + "Principal");
		addVarBtn = new JButton(action);
		addVarBtn.setHorizontalAlignment(SwingConstants.LEFT);
		addVarBtn.setUI(new BlueishButtonUI());
		addVarBtn.setPreferredSize(new Dimension(95, 25));
		GridBagConstraints gbc_addVarBtn = new GridBagConstraints();
		gbc_addVarBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_addVarBtn.insets = new Insets(3, 3, 3, 3);
		gbc_addVarBtn.gridy = 0;
		container.add(addVarBtn, gbc_addVarBtn);
	}

	private void initContainer() {
		container = new JPanel();
		container.setOpaque(false);
		add(container, BorderLayout.CENTER);
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[] { 0, 0, 0 };
		gbl_container.rowHeights = new int[] { 0, 0, 0 };
		gbl_container.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_container.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		container.setLayout(gbl_container);
	}

	public void addedVariable(String id) {
		varPanel.add(Services.getService().getRenderer().paint(id));
		varPanel.revalidate();
		varPanel.repaint();
	}

	public void changeVariable(String id) {
	}

	public void removedVariable(String id) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if (variable != null) {
			varPanel.remove(variable);
		}
		varPanel.revalidate();
		varPanel.repaint();
		
	}

	public void changeVariableName(String id, String name, String lastName) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if (variable != null) {
			variable.setVariableName(name);
		}

	}

	public void changeVariableValue(String id, String value) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if(variable!=null){
			variable.setVariableValue(value);
		}
	}

	public void changeVariableType(String id, short type) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if(variable!=null){
			variable.setVariableType(type);
		}
	}

	public void variableRestored(String id) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		variable.setVisible(true);
		varPanel.revalidate();
		varPanel.repaint();
	}

}
