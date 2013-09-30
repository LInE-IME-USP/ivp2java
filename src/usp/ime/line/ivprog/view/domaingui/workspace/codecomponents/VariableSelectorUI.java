package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.utils.IVPVariableMap;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class VariableSelectorUI extends JPanel implements IVariableListener {
	
	public static final Color borderColor = new Color(230, 126, 34); 
	public static final Color bgColor = new Color(236, 240, 241);
	public static final Color hoverColor = new Color(241, 196, 15);
	
	private JComboBox configMenu;
	private JLabel initialLabel;
	private String parent;
	
	public VariableSelectorUI(String parent){
		this.parent = parent;
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
		initialLabel = new JLabel(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
		initialLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(initialLabel);
	}
	
	private void initConfigMenu() {
		configMenu = new JComboBox();
		configMenu.setVisible(false);
		initValues();
		configMenu.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent evt) {
				JComboBox cb = (JComboBox) evt.getSource();
			    Object item = evt.getItem();
			    if (evt.getStateChange() == ItemEvent.SELECTED) {
			    	configMenu.setVisible(false);
			    	initialLabel.setText((String) item);
			    	initialLabel.setVisible(true);
			    } 
			}
		});
		
		add(configMenu);
	}

	private void initValues() {
		CodeComponent component = (CodeComponent) Services.getService().getModelMapping().get(parent);
		Function f = (Function) Services.getService().getModelMapping().get(component.getScopeID());
		Vector variables = f.getLocalVariableMap().toVector();
		for(int i = 0; i < variables.size(); i++){
			String name = ((Variable) Services.getService().getModelMapping().get(variables.get(i))).getVariableName();
			configMenu.addItem(name);
		}
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
			selectVariableAction();
			configMenu.requestFocus();
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	public void selectVariableAction() {
		configMenu.setVisible(true);
		initialLabel.setVisible(false);
		revalidate();
		repaint();
	}
	
	//BEGIN: Variable listener methods
	public void addedVariable(String id) { 
		
	}
	public void changeVariable(String id) { }
	public void removedVariable(String id) { }
	public void changeVariableName(String id, String name) { }
	public void changeVariableValue(String id, String value) { }
	public void changeVariableType(String id, short type) { }
	//END: Variable listener methods
}
