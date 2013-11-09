package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AttributionLineUI extends CodeBaseUI {

	private JPanel contentPanel;
	private JLabel codeLabel;
	private VariableSelectorUI varSelector;
	private ExpressionHolderUI expression;
	private static Color bgColor = new Color(199, 215, 219);
	
	public AttributionLineUI(String id, String scope, String parent){
		setModelID(id);
		setModelParent(parent);
		setModelScope(scope);
		initialization();
		addComponents();
	}
	
	private void initialization(){
		expression = new ExpressionHolderUI(getModelID(), getModelScope());
		contentPanel = new JPanel(new DynamicFlowLayout(FlowLayout.LEFT, this, this.getClass(),1));
		codeLabel = new JLabel(ResourceBundleIVP.getString("attLineText"));
		varSelector = new VariableSelectorUI(getModelParent());
		setBackground(bgColor);
	}
	
	private void addComponents(){
		contentPanel.setOpaque(false);
		contentPanel.add(varSelector);
		contentPanel.add(codeLabel);
		contentPanel.add(expression);
		addContentPanel(contentPanel);
	}
	
}
