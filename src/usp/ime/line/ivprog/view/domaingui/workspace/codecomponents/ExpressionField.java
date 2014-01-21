package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import usp.ime.line.ivprog.Services;

public class ExpressionField extends JPanel {
	
	private JButton btnEdit;
	private ExpressionHolderUI expressionHolderUI;
	private boolean isEditing = false;

	public ExpressionField(String parent, String scope) {
		initLayout();
		initExpressionHolder(parent, scope);
		initEditionBtn();
	}

	private void initEditionBtn() {
		btnEdit = new JButton("edit");
		Action edition = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(isEditing){
					expressionHolderUI.disableEdition();
					isEditing = false;
				}else{
					expressionHolderUI.enableEdition();
					isEditing = true;
				}
			}
		};
		btnEdit.setAction(edition);
		add(btnEdit);
	}

	private void initExpressionHolder(String parent, String scope) {
		expressionHolderUI = new ExpressionHolderUI(parent, scope);
		add(expressionHolderUI);
	}

	private void initLayout() {
		setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
	}

	public void setHolderContent(JComponent expression){
		expressionHolderUI.setExpression(expression);
	}
	
	public void setComparison(boolean isComparison){
		expressionHolderUI.setComparison(isComparison);
	}
	
	public void setEdition(boolean edit){
		isEditing = edit;
	}
	
	public boolean isEdition(){
		return isEditing;
	}
}
