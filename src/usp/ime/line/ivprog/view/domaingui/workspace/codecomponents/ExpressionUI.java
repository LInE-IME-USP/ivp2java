package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

public class ExpressionUI extends JPanel {
	
	private JLabel leftPar;
	private ExpressionHolderUI expressionBaseUI_1;
	private JLabel expSignal;
	private ExpressionHolderUI expressionBaseUI_2;
	private JLabel rightPar;
	private JButton optionsBtn;
	private String parentID;
	private String scopeID;

	public ExpressionUI(String parent, String scope) {
		parentID = parent;
		scopeID = scope;
		initLayout();
		initComponents();
	}

	private void initComponents() {
		leftPar = new JLabel("(");
		add(leftPar);
		expSignal = new JLabel();
		add(expSignal);
		rightPar = new JLabel(")");
		add(rightPar);
		optionsBtn = new JButton("^");
		add(optionsBtn);
	}

	private void initLayout() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
	}

	public ExpressionHolderUI getExpressionBaseUI_1() {
		return expressionBaseUI_1;
	}

	public void setExpressionBaseUI_1(ExpressionHolderUI expressionBaseUI_1) {
		this.expressionBaseUI_1 = expressionBaseUI_1;
		add(expressionBaseUI_1 , 1);
		revalidate();
		repaint();
	}

	public ExpressionHolderUI getExpressionBaseUI_2() {
		return expressionBaseUI_2;
	}

	public void setExpressionBaseUI_2(ExpressionHolderUI expressionBaseUI_2) {
		this.expressionBaseUI_2 = expressionBaseUI_2;
		if(expressionBaseUI_1 != null){
			 add(expressionBaseUI_2,3);
		} else {
			 add(expressionBaseUI_2,2);
		}
		revalidate();
		repaint();
	}
	
	public void setSignal(String sig){
	
	}

}
