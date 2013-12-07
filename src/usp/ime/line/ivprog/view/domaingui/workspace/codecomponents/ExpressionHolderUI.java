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
	private JPopupMenu contentMenu;
	private JPopupMenu operationMenu;
	private JLabel selectLabel;
	private String parentModelID;
	private String scopeModelID;
	private String currentModelID;
	private JButton operationsBtn;
	private JComponent expression;
	private String operationContext;
	
	public ExpressionHolderUI(String parent, String scopeID){
		init(parent, scopeID);
		initialization();
		initComponents();
	}

	//BEGIN: initialization methods
	private void init(String parent, String scopeID) {
		parentModelID = parent;
		scopeModelID = scopeID;
		setOperationContext("");
		Services.getService().getController().getProgram().addExpressionListener(this);
	}

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
		operationMenu = new JPopupMenu();
		Action createAddition = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_ADDITION, operationContext);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createAddition.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.tip"));
		createAddition.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createAddition.text"));
		Action createSubtraction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_SUBTRACTION, operationContext);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createSubtraction.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.tip"));
		createSubtraction.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createSubtraction.text"));
		Action createMultiplication = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_MULTIPLICATION, operationContext);
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		createMultiplication.putValue(Action.SHORT_DESCRIPTION,ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.tip"));
		createMultiplication.putValue(Action.NAME, ResourceBundleIVP.getString("ExpressionBaseUI.action.createMultiplication.text"));
		Action createDivision = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String expressionID = ((IDomainObjectUI)expression).getModelID();
				Services.getService().getController().createExpression(expressionID, parentModelID,  Expression.EXPRESSION_OPERATION_DIVISION, operationContext);
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
		
		operationMenu.add(createAddition);
		operationMenu.add(createSubtraction);
		operationMenu.add(createDivision);
		operationMenu.add(createMultiplication);
		operationMenu.add(cleanContent);
		
	}

	private void initChangeContentBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				operationMenu.show(operationsBtn, operationsBtn.getWidth(), operationsBtn.getHeight());
			}
		};
		action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/operations.png")));
		operationsBtn = new JButton(action);
		operationsBtn.setUI(new IconButtonUI());
		operationsBtn.setVisible(false);
		add(operationsBtn);		
	}

	private void initLabel() {
		selectLabel = new JLabel(ResourceBundleIVP.getString("ExpressionBaseUI.selectLabel.text"));
		selectLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(selectLabel);
	}
	
	private void initChooseContentMenu() {
		contentMenu = new JPopupMenu();
		Action variableHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().createExpression(null,parentModelID,Expression.EXPRESSION_VARIABLE, operationContext);
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
		contentMenu.add(variableHasBeenChosen);
		contentMenu.add(integerHasBeenChosen);
		contentMenu.add(doubleHasBeenChosen);
		contentMenu.add(textHasBeenChosen);
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
			contentMenu.show(container, 0, container.getHeight());
			contentMenu.requestFocus();
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	//BEGIN: Expression listener methods
	public void expressionCreated(String holder, String id,String context) {
		if(holder == parentModelID && operationContext.equals(context)){
			JComponent lastExp = expression;
			if(expression != null)
				remove(expression);
			currentModelID = id;
			expression = Services.getService().getRenderer().paint(id);
			populatedExpressionHolder();
			add(expression,0);
			if(expression instanceof VariableSelectorUI){
				((VariableSelectorUI)expression).editStateOn();
			}else{
				((OperationUI)expression).setExpressionBaseUI_1(lastExp);
				if(lastExp instanceof VariableSelectorUI)
					((VariableSelectorUI)lastExp).editStateOn();
			}
			revalidate();
			repaint();
		} 
	}
	
	public void expressionDeleted(String id, String context) {
		if(expression != null){
			if(currentModelID.equals(id) && operationContext.equals(context)){
				remove(expression);
				if(expression instanceof OperationUI){
					JComponent exp = ((OperationUI)expression).getExpressionBaseUI_1().getExpression();
					setExpression(exp);
				}else{
					emptyExpressionHolder();
				}
				revalidate();
				repaint();
			}
		}
	}
	
	public void cleanExpressionField() {
		
	}

	public void expressionRestored(String holder, String id, String context) {
		String lastExpID = null;
		if (holder.equals(parentModelID) && operationContext.equals(context)) {
			JComponent restoredExp = (JComponent) Services.getService().getViewMapping().get(id);
			if (restoredExp instanceof OperationUI){
				((OperationUI) restoredExp).setExpressionBaseUI_1(expression);
			} else {
				if(((VariableSelectorUI) restoredExp).isEditState()){
					editStateOn();
				}else{
					editStateOff();
				}
			}
			setExpression(restoredExp);
		}
		revalidate();
		repaint();
	}
	
	public void expressionTypeChanged(String id, String context) {
		if (currentModelID.equals(id) && operationContext.equals(context)) {
			
		}
	}

	//END: Expression listener methods

	private void emptyExpressionHolder(){
		selectLabel.setVisible(true);
		setOpaque(true);
		drawBorder = true;
		editStateOff();
	}
	
	private void populatedExpressionHolder(){
		selectLabel.setVisible(false);
		setOpaque(false);
		drawBorder = false;
	}
	
	public JComponent getExpression() {
		return expression;
	}

	public void setExpression(JComponent exp) {
		currentModelID = ((IDomainObjectUI)exp).getModelID();;
		this.expression = exp;
		populatedExpressionHolder();
			editStateOn();
		add(this.expression, 0);
		
		revalidate();
		repaint();
	}
	
	private boolean isEditing(){
		if(expression instanceof VariableSelectorUI)
			return ((VariableSelectorUI)expression).isEditState();
		else
			return true;
	}

	public String getCurrentModelID() {
		return currentModelID;
	}

	public void setCurrentModelID(String currentModelID) {
		this.currentModelID = currentModelID;
	}
	
	public String getOperationContext() {
		return operationContext;
	}

	public void setOperationContext(String operationContext) {
		this.operationContext = operationContext;
	}
	
	//Teste 
	public void editStateOff(){
		operationsBtn.setVisible(false);
		revalidate();
		repaint();
	}
	
	public void editStateOn(){
		operationsBtn.setVisible(true);
		revalidate();
		repaint();
	}

	
	

}
