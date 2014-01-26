package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class PrintUI extends CodeBaseUI {

	private JPanel contentPanel;
	private ExpressionHolderUI expressionHolder;
	private JLabel codeBlockName;
	private String context;
	
	public PrintUI(String id, String parentID, String scopeID){
		setModelParent(parentID);
		setModelScope(scopeID);
		setModelID(id);
		initialization(id);
		addComponents();
	}
	
	private void initialization(String id){
		expressionHolder = new ExpressionHolderUI(getModelID(), getModelScope());
		contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		codeBlockName = new JLabel(ResourceBundleIVP.getString("printTitle"));
		setBackground(FlatUIColors.MAIN_BG);
	}
	
	private void addComponents(){
		contentPanel.setOpaque(false);
		contentPanel.add(codeBlockName);
		contentPanel.add(expressionHolder);
		addContentPanel(contentPanel);
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}
}
