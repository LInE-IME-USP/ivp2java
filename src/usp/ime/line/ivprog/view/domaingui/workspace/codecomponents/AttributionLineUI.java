package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AttributionLineUI extends CodeBaseUI {

	private JPanel contentPanel;
	private JLabel codeLabel;
	private VariableSelectorUI varSelector;
	private ExpressionHolderUI expression;
	private static Color bgColor = new Color(199, 215, 219);
	
	public AttributionLineUI(String id, String scope){
		setThisID(id);
		setParentID(scope);
		initialization();
		addComponents();
	}
	
	private void initialization(){
		expression = new ExpressionHolderUI(getParentID());
		contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		codeLabel = new JLabel(ResourceBundleIVP.getString("attLineText"));
		varSelector = new VariableSelectorUI(getParentID());
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
