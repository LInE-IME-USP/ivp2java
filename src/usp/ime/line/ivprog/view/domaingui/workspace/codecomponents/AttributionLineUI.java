package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AttributionLineUI extends CodeBaseUI {

	private JPanel contentPanel;
	private JLabel codeLabel;
	private VariableSelectorUI varSelector;
	private ExpressionFieldUI expression;
	private String context;
	
	public AttributionLineUI(String id, String scope, String parent){
		setModelID(id);
		setModelParent(parent);
		setModelScope(scope);
		initialization();
		addComponents();
	}
	
	private void initialization(){
		expression = new ExpressionFieldUI(getModelID(), getModelScope());
		contentPanel = new JPanel();
		codeLabel = new JLabel(ResourceBundleIVP.getString("AttLine.text"));
		varSelector = new VariableSelectorUI(getModelParent());
		varSelector.setModelScope(getModelScope());
		varSelector.setIsolationMode(true);
		setBackground(FlatUIColors.MAIN_BG);
	}
	
	private void addComponents(){
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPanel.add(varSelector);
		contentPanel.add(codeLabel);
		contentPanel.add(expression);
		addContentPanel(contentPanel);
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

	public void setLeftVarModelID(String leftVariableID) {
		varSelector.setModelID(leftVariableID);
	}
	
}
