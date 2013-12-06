package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OperationUI extends JPanel implements IDomainObjectUI {
	
	private JLabel leftPar;
	private ExpressionHolderUI expressionBaseUI_1;
	private JLabel expSign;
	private ExpressionHolderUI expressionBaseUI_2;
	private JLabel rightPar;
	
	private String currentModelID;
	private String parentModelID;
	private String scopeModelID;
	private String context; 
	private JPanel expPanel;
	
	private boolean drawBorder = false;

	public OperationUI(String parent, String scope, String id) {
		parentModelID = parent;
		scopeModelID = scope;
		currentModelID = id;
		setOpaque(false);
		initLayout();
		initComponents();
		initSignal();
	}
	
	private void initSignal() {
		String sign = null;
		Operation op = (Operation) Services.getService().getModelMapping().get(currentModelID);
		short type = op.getOperationType();
		if(type == Expression.EXPRESSION_OPERATION_ADDITION){
			sign = "\u002B";
		}else if (type == Expression.EXPRESSION_OPERATION_DIVISION){
			sign = "\u00F7";
		}else if (type == Expression.EXPRESSION_OPERATION_MULTIPLICATION){
			sign = "\u00D7";
		}else if (type == Expression.EXPRESSION_OPERATION_SUBTRACTION){
			sign = "\u002D";
		}
		expSign.setText(sign);
	}

	private void initComponents() {
		initLeftParenthesis();
		initExpressionHolder1();
		initExpressionSign();
		initExpressionHolder2();
		initRightParenthesis();
	}

	private void initRightParenthesis() {
		rightPar = new JLabel(")");
		rightPar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(rightPar);
	}

	private void initExpressionHolder2() {
		expressionBaseUI_2 = new ExpressionHolderUI(currentModelID, scopeModelID);
		expressionBaseUI_2.setOperationContext("right");
		add(expressionBaseUI_2);
	}

	private void initExpressionSign() {
		expPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) expPanel.getLayout();
		flowLayout.setHgap(3);
		flowLayout.setVgap(0);
		expPanel.addMouseListener(new OperationMouseListener(expPanel));
		add(expPanel);
		expSign = new JLabel();
		expPanel.add(expSign);
		expSign.setFont(new Font("Tahoma", Font.PLAIN, 16));
	}

	private void initExpressionHolder1() {
		expressionBaseUI_1 = new ExpressionHolderUI(currentModelID, scopeModelID);
		expressionBaseUI_1.setOperationContext("left");
		add(expressionBaseUI_1);
	}

	private void initLeftParenthesis() {
		leftPar = new JLabel("(");
		leftPar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(leftPar);
	}

	private void initLayout() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(3);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
	}

	public ExpressionHolderUI getExpressionBaseUI_1() {
		return expressionBaseUI_1;
	}

	public void setExpressionBaseUI_1(JComponent expressionBaseUI_1) {
		this.expressionBaseUI_1.setExpression(expressionBaseUI_1);
		revalidate();
		repaint();
	}

	public ExpressionHolderUI getExpressionBaseUI_2() {
		return expressionBaseUI_2;
	}

	public void setExpressionBaseUI_2(JComponent expressionBaseUI_2) {
		this.expressionBaseUI_2.setExpression(expressionBaseUI_2);
		revalidate();
		repaint();
	}
	
	public void setSignal(String sig){
	
	}

	public String getModelID() {
		return currentModelID;
	}
	
	public String getModelParent() {
		return parentModelID;
	}

	public String getModelScope() {
		return scopeModelID;
	}

	public void setModelID(String id) {
		currentModelID = id;
		initSignal();
	}

	public void setModelParent(String id) {
		parentModelID = id;
	}

	public void setModelScope(String id) {
		scopeModelID = id;
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}
	
	private class OperationMouseListener implements MouseListener {
		private JPanel panel;
		public OperationMouseListener(JPanel p){ panel = p; }
		public void mouseClicked(MouseEvent arg0) { }
		public void mouseEntered(MouseEvent arg0) { panel.setBackground(ExpressionHolderUI.hoverColor);}
		public void mouseExited(MouseEvent arg0) { panel.setBackground(ExpressionHolderUI.bgColor); }
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
	}
	
	


}
