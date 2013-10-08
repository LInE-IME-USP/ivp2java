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
import javax.swing.ImageIcon;
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
	
	private JComboBox variableList;
	private JLabel initialLabel;
	private JLabel icon; 
	private String parent;
	private Vector itemVector;
	private boolean isUpdate = true;
	private boolean warningState = false;
	private JLabel iconLabel;
	
	
	public VariableSelectorUI(String parent){
		this.parent = parent;
		initialization();
		initComponents();
		//Starts listening to variable changes
		Services.getService().getController().getProgram().addVariableListener(this);
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
		initVector();
		initLabel();
		initConfigMenu();
		initIconLabel();
	}

	private void initIconLabel() {
		iconLabel = new JLabel();
		iconLabel.setIcon(new ImageIcon(VariableSelectorUI.class.getResource("/usp/ime/line/resources/icons/attention.png")));
		add(iconLabel);
		iconLabel.setVisible(false);
	}

	private void initVector() {
		itemVector = new Vector();
	}

	private void initLabel() {
		initialLabel = new JLabel(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
		initialLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(initialLabel);
	}
	
	private void initConfigMenu() {
		variableList = new JComboBox();
		variableList.setVisible(false);
		initValues();
		variableList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				System.out.println(evt);
			    if(!isUpdate){
					JComboBox cb = (JComboBox) evt.getSource();
				    Object item = cb.getSelectedItem();
			    	if (evt.getActionCommand().equals("comboBoxChanged")) {
			    		variableList.setVisible(false);
			    		initialLabel.setText((String) item);
			    		initialLabel.setVisible(true);
			    		if(warningState){
			    			turnWaningStateOFF();
			    		}
			    	} 
			    }
			}
		});
		
		add(variableList);
	}

	private void initValues() {
		CodeComponent component = (CodeComponent) Services.getService().getModelMapping().get(parent);
		Function f = (Function) Services.getService().getModelMapping().get(component.getScopeID());
		Vector variables = f.getLocalVariableMap().toVector();
		for(int i = 0; i < variables.size(); i++){
			String name = ((Variable) Services.getService().getModelMapping().get(variables.get(i))).getVariableName();
			itemVector.add(name);
			variableList.addItem(name);
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
			variableList.requestFocus();
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	public void selectVariableAction() {
		variableList.setVisible(true);
		initialLabel.setVisible(false);
		revalidate();
		repaint();
	}
	
	//BEGIN: Variable listener methods
	public void addedVariable(String id) { 
		String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
		itemVector.add(name);
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
	}
	
	public void changeVariable(String id) { }
	public void removedVariable(String id) { 
		String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
		itemVector.remove(name);
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
		if(initialLabel.isVisible()||itemVector.size()==0){
			if(name.equals(initialLabel.getText())){
				turnWaningStateON();
			}
		}
	}

	public void changeVariableName(String id, String name, String lastName) {
		int index = itemVector.indexOf(lastName);
		initialLabel.setText(name);
		initialLabel.revalidate();
		initialLabel.repaint();
		itemVector.remove(lastName);
		itemVector.add(index, name);
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
	}
	public void changeVariableValue(String id, String value) { }
	public void changeVariableType(String id, short type) { }
	//END: Variable listener methods
	
	//BEGIN: support methods
	private void turnWaningStateON() {
		iconLabel.setVisible(true);
		selectVariableAction();
		warningState = true;
	}
	
	private void turnWaningStateOFF() {
		iconLabel.setVisible(false);
		warningState = false;
	}
	
	
	private void updateVariableList(){
		variableList.removeAllItems();
		for(int i = 0; i < itemVector.size(); i++){
			variableList.addItem(itemVector.get(i));
		}
	}

	@Override
	public void variableRestored(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
