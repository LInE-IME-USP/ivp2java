package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.utils.IconButtonUI;

public class ExpressionFieldUI extends JPanel {
	
	private JButton btnEdit;
	private ExpressionHolderUI expressionHolderUI;
	private boolean isEditing = false;
	private ImageIcon open;
	private ImageIcon closed;
	private JLabel teste;
	
	
	public ExpressionFieldUI(String parent, String scope) {
		initLayout();
		initExpressionHolder(parent, scope);
		initEditionBtn();
	}

	private void initEditionBtn() {
		
		open = new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/locker_opened.png"));
		closed = new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/locker_closed.png"));
		teste = new JLabel();
		teste.setIcon(closed);
		Action edition = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(isEditing){
					expressionHolderUI.disableEdition();
					isEditing = false;
					teste.setIcon(closed);
					teste.repaint();
				}else{
					expressionHolderUI.enableEdition();
					isEditing = true;
					teste.setIcon(open);
					teste.repaint();
				}
			}
		};
		btnEdit = new JButton(edition);
		btnEdit.add(teste);
		btnEdit.setIcon(new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/pog.png")));
		btnEdit.setUI(new IconButtonUI());
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
		if(edit){
			teste.setIcon(open);
		}else{
			teste.setIcon(closed);
		}
		isEditing = edit;
	}
	
	public boolean isEdition(){
		return isEditing;
	}
	
}
