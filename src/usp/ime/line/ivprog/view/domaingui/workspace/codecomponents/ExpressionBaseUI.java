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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.editinplace.EditInPlace;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExpressionBaseUI extends JPanel implements IVariableListener {
	
	public static final Color borderColor = new Color(230, 126, 34); 
	public static final Color bgColor = new Color(236, 240, 241);
	public static final Color hoverColor = new Color(241, 196, 15);
	
	private boolean drawBorder = true;
	
	private JPopupMenu chooseContent;
	private JLabel selectLabel;
	
	private String parent;
	
	private EditInPlace integerEdit;
	private JButton btnChangeContent;
	
	public ExpressionBaseUI(String parent){
		this.parent = parent;
		initialization();
		initComponents();
	}

	//BEGIN: initialization methods
	private void initialization() {
		setBackground(bgColor);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		setLayout(flowLayout);
		addMouseListener(new ExpressionMouseListener(this));
	}
	
	private void initComponents() {
		initLabel();
		initChooseContentMenu();
	}

	private void initLabel() {
		selectLabel = new JLabel(ResourceBundleIVP.getString("expressionBaseInitialLabel"));
		selectLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(selectLabel);
	}
	
	private void initChooseContentMenu() {
		chooseContent = new JPopupMenu();
		Action variableHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				selectLabel.setVisible(false);
				setOpaque(false);
				add(new VariableSelectorUI(parent));
				drawBorder = false;
			}
		};
		//setVarAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		variableHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,"Escolhe uma variável dentre as possíveis.");
		variableHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertVariable"));
		Action integerHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		integerHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um número inteiro.");
		integerHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertInteger"));
		Action doubleHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		doubleHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um número real.");
		doubleHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertDouble"));
		Action textHasBeenChosen = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		textHasBeenChosen.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um texto qualquer.");
		textHasBeenChosen.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertText"));
		chooseContent.add(variableHasBeenChosen);
		chooseContent.add(integerHasBeenChosen);
		chooseContent.add(doubleHasBeenChosen);
		chooseContent.add(textHasBeenChosen);
	}

	//END: initialization methods
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(drawBorder){
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
			FlowLayout layout = (FlowLayout) getLayout();
			layout.setVgap(3);
			layout.setHgap(3);
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
	
	//BEGIN: Variable listener methods
	public void addedVariable(String id) { }
	public void changeVariable(String id) { }
	public void removedVariable(String id) { }
	public void changeVariableName(String id, String name, String lastName) { }
	public void changeVariableValue(String id, String value) { }
	public void changeVariableType(String id, short type) { }
	//END: Variable listener methods

	public void variableRestored(String id) {
		
	}
}
