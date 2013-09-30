package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class PrintUI extends CodeBaseUI {

	private JPanel contentPanel;
	private ExpressionBase expressionHolder;
	private JLabel codeBlockName;
	
	private static Color bgColor = new Color(159, 165, 169);
	
	public PrintUI(String id){
		setThisID(id);
		initialization(id);
		addComponents();
	}
	
	private void initialization(String id){
		expressionHolder = new ExpressionBase(id);
		contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		codeBlockName = new JLabel(ResourceBundleIVP.getString("printTitle"));
		setBackground(bgColor);
	}
	
	private void addComponents(){
		contentPanel.setOpaque(false);
		contentPanel.add(codeBlockName);
		contentPanel.add(expressionHolder);
		addContentPanel(contentPanel);
	}
	
}
