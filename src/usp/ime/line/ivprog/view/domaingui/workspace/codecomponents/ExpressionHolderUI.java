package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.editinplace.EditInPlace;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExpressionHolderUI extends JPanel implements IExpressionListener {
	
	public static final Color borderColor = new Color(230, 126, 34); 
	public static final Color bgColor = new Color(236, 240, 241);
	public static final Color hoverColor = new Color(241, 196, 15);
	
	private boolean drawBorder = true;
	
	private JPopupMenu chooseContent;
	private JPopupMenu changeContent;
	
	private JLabel selectLabel;
	
	private String parentModelID;
	private String scopeModelID;
	private String currentModelID;
	
	private EditInPlace integerEdit;
	
	private JButton btnChangeContent;
	
	private JComponent expression;
	//talvez não use
	private JComponent expToBeRestored;
	
	//TODO: VERIFICAR LEFT RIGHT !!! É O QUE ESTÁ DANDO PROBLEMA AGORA. RESOLVENDO ISSO, ACABOU A PARTE DE EXPRESSÃO.
	public ExpressionHolderUI(String parent, String scopeID){
		parentModelID = parent;
		scopeModelID = scopeID;
		initialization();
		initComponents();
		Services.getService().getController().getProgram().addExpressionListener(this);
	}

	//BEGIN: initialization methods
	private void initialization() {
		setBackground(bgColor);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setHgap(3);
		flowLayout.setVgap(0);
		setLayout(flowLayout);
		addMouseListener(new ExpressionMouseListener(this));
	}
	
	private void initComponents() {
		initLabel();
		initChooseContentMenu();
		initChangeContentBtn();
		initChangeContentMenu();
	}
	
	
	private void initChangeContentMenu() {
		changeContent = new JPopupMenu();
		Action createAddition = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_ADDITION);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createAddition.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.tip"));
		createAddition.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.text"));
		Action createSubtraction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_SUBTRACTION);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createSubtraction.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.tip"));
		createSubtraction.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.text"));
		Action createMultiplication = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_MULTIPLICATION);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createMultiplication.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.tip"));
		createMultiplication.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.text"));
		Action createDivision = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_DIVISION);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createDivision.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createDivision.tip"));
		createDivision.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createDivision.text"));
		Action cleanContent = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		cleanContent.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.tip"));
		cleanContent.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.cleanContent.text"));
		
		changeContent.add(createAddition);
		changeContent.add(createSubtraction);
		changeContent.add(createDivision);
		changeContent.add(createMultiplication);
		changeContent.add(cleanContent);
		
	}

	private void initChangeContentBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				changeContent.show(btnChangeContent, btnChangeContent.getWidth(), btnChangeContent.getHeight());
			}
		};
		action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/operations.png")));
		btnChangeContent = new JButton(action);
		btnChangeContent.setUI(new IconButtonUI());
		btnChangeContent.setEnabled(false);
		add(btnChangeContent);		
	}

	private void initLabel() {
		selectLabel = new JLabel(ResourceBundleIVP.getString("ExpressionBaseUI.selectLabel.text"));
		selectLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(selectLabel);
	}
	
	private void initChooseContentMenu() {
		chooseContent = new JPopupMenu();
		Action variableHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().createExpression(null,parentModelID,Expression.EXPRESSION_VARIABLE);
			}
		};
		//setVarAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		variableHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.variableHasBeenChosen.tip"));
		variableHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.variableHasBeenChosen.text"));
		Action integerHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		integerHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.integerHasBeenChosen.tip"));
		integerHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.integerHasBeenChosen.text"));
		Action doubleHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		doubleHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.doubleHasBeenChosen.tip"));
		doubleHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.doubleHasBeenChosen.text"));
		Action textHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		textHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.textHasBeenChosen.tip"));
		textHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.textHasBeenChosen.text"));
		chooseContent.add(variableHasBeenChosen);
		chooseContent.add(integerHasBeenChosen);
		chooseContent.add(doubleHasBeenChosen);
		chooseContent.add(textHasBeenChosen);
	}

	//END: initialization methods
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(drawBorder){
			FlowLayout layout = (FlowLayout) getLayout();
			layout.setVgap(3);
			layout.setHgap(3);
			revalidate();
			g.setColor(borderColor);
			java.awt.Rectangle bounds = getBounds();
			for (int i = 0; i < bounds.width; i += 6) {
				g.drawLine(i, 0, i + 3, 0);
				g.drawLine(i + 3, bounds.height - 1, i + 6, bounds.height - 1);
			}
			for (int i = 0; i < bounds.height; i += 6) {
				g.drawLine(0, i, 0, i + 3);
				g.drawLine(bounds.width - 1, i + 3, bounds.width - 1, i + 6);
			}
		}else{
			FlowLayout layout = (FlowLayout) getLayout();
			layout.setVgap(0);
			layout.setHgap(0);
		}
	}
	
	//BEGIN: Mouse listener
	private class ExpressionMouseListener implements MouseListener {
		private JPanel container;
		public ExpressionMouseListener(JPanel c){ container = c; }

		public void mouseEntered(MouseEvent e) {
			setBackground(hoverColor);
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		}
		
		public void mouseExited(MouseEvent e) {
			setBackground(bgColor);
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}
		public void mouseClicked(MouseEvent arg0) {
			chooseContent.show(container, 0, container.getHeight());
			chooseContent.requestFocus();
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	//BEGIN: Expression listener methods
	
	public void expressionCreated(String holder, String id) {
		if(holder == parentModelID){
			JComponent lastExp = expression;
			drawBorder = false;
			if(expression != null)
				remove(expression);
			currentModelID = id;
			expression = Services.getService().getRenderer().paint(id);
			selectLabel.setVisible(false);
			setOpaque(false);
			if(expression instanceof VariableSelectorUI){
				((VariableSelectorUI)expression).selectVariableAction();
			}else{
				((OperationUI)expression).setExpressionBaseUI_1(lastExp);
			}
			add(expression,0);
			expToBeRestored = expression;
			if(expression!=null)
				btnChangeContent.setEnabled(true);
			revalidate();
			repaint();
		} 
	}
	//END: Expression listener methods

	public JComponent getExpression() {
		return expression;
	}

	public void setExpression(JComponent expression) {
		String expressionID = ((IDomainObjectUI)expression).getModelID();
		currentModelID = expressionID;
		this.expression = expression;
		selectLabel.setVisible(false);
		drawBorder = false;
		setOpaque(false);
		add(expression,0);
		if(expression!=null)
			btnChangeContent.setEnabled(true);
		revalidate();
		repaint();
	}

	public String getCurrentModelID() {
		return currentModelID;
	}

	public void setCurrentModelID(String currentModelID) {
		this.currentModelID = currentModelID;
	}

	public void expressionDeleted(String id) {
		if(expression!=null){
			String expressionID = ((IDomainObjectUI)expression).getModelID();
			if(expressionID.equals(id)){
				remove(expression);
				if(expression instanceof OperationUI){
					expression = ((OperationUI)expression).getExpressionBaseUI_1().getExpression();
					setExpression(expression);
				}else{
					selectLabel.setVisible(true);
					setOpaque(true);
					drawBorder = true;
				}
				revalidate();
				repaint();
			}
		}
	}

	public void cleanExpressionField() {
		
	}

	public void expressionRestored(String holder, String id) {
		String lastExpID = null;
		if(expToBeRestored != null){
			lastExpID = ((IDomainObjectUI)expToBeRestored).getModelID();
			System.out.println("lastExpID > "+lastExpID+" id"+id+" result "+lastExpID.equals(id));
			if(lastExpID.equals(id)){
				JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
				if(restoredExp instanceof OperationUI)
					((OperationUI)restoredExp).setExpressionBaseUI_1(expression);
				setExpression(restoredExp);
			}
		} 
	}

}
