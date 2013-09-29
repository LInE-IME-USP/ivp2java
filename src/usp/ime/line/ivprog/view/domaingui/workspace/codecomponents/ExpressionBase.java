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
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExpressionBase extends JPanel implements IVariableListener {
	
	public static final Color borderColor = new Color(230, 126, 34); 
	public static final Color bgColor = new Color(236, 240, 241);
	public static final Color hoverColor = new Color(241, 196, 15);
	
	private JPopupMenu configMenu;
	private JLabel initialLabel;
	
	public ExpressionBase(){
		initialization();
		initComponents();
	}

	//BEGIN: initialization methods
	private void initialization() {
		setBackground(bgColor);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(3);
		flowLayout.setHgap(3);
		setLayout(flowLayout);
		addMouseListener(new ExpressionMouseListener(this));
	}
	
	private void initComponents() {
		initLabel();
		initConfigMenu();
	}

	private void initLabel() {
		initialLabel = new JLabel(ResourceBundleIVP.getString("expressionBaseInitialLabel"));
		initialLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(initialLabel);
	}
	
	private void initConfigMenu() {
		configMenu = new JPopupMenu();
		Action setVarAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setVarAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		setVarAction.putValue(Action.SHORT_DESCRIPTION,"Escolhe uma variável dentre as possíveis.");
		setVarAction.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertVariable"));
		Action setIntegerAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		setIntegerAction.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um número inteiro.");
		setIntegerAction.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertInteger"));
		Action setDoubleAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		setDoubleAction.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um número real.");
		setDoubleAction.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertDouble"));
		Action setTextAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		//setConstantAction.putValue(Action.SMALL_ICON, new ImageIcon(ExpressionBase.class.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		setTextAction.putValue(Action.SHORT_DESCRIPTION,"Você poderá inserir um texto qualquer.");
		setTextAction.putValue(Action.NAME, ResourceBundleIVP.getString("expBaseInsertText"));
		configMenu.add(setVarAction);
		configMenu.add(setIntegerAction);
		configMenu.add(setDoubleAction);
		configMenu.add(setTextAction);
		
	}

	//END: initialization methods
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
	}
	
	//BEGIN: Mouse listener
	private class ExpressionMouseListener implements MouseListener {
		private JPanel container;
		public ExpressionMouseListener(JPanel c){ container = c; }

		public void mouseEntered(MouseEvent e) {
			setBackground(hoverColor);
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		}
		
		public void mouseExited(MouseEvent e) {
			setBackground(bgColor);
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}
		public void mouseClicked(MouseEvent arg0) {
			configMenu.show(container, 0, container.getHeight());
			configMenu.requestFocus();
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	//BEGIN: Variable listener methods
	public void addedVariable(String id) { }
	public void changeVariable(String id) { }
	public void removedVariable(String id) { }
	public void changeVariableName(String id, String name) { }
	public void changeVariableValue(String id, String value) { }
	public void changeVariableType(String id, short type) { }
	//END: Variable listener methods
}
