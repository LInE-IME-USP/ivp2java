package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.Component;
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
import java.util.TreeMap;
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
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.utils.IVPVariableMap;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class VariableSelectorUI extends JPanel implements IVariableListener, IDomainObjectUI {
	
	public static final Color borderColor = new Color(230, 126, 34); 
	public static final Color bgColor = new Color(255, 255, 255);
	public static final Color hoverColor = new Color(241, 196, 15);
	
	private JComboBox varList;
	private TreeMap indexMap;
	private JLabel nameLabel;
	private JLabel icon; 
	private boolean isUpdate = true;
	private boolean warningState = false;
	private boolean isOnlyOneElement = false;
	private boolean isIsolated = false;

	private JLabel iconLabel;
	private String currentModelID;
	private String parentModelID;
	private String scopeModelID;
	private String context;
	
	private boolean drawBorder = true;
	
	private boolean editState = true;
	
	public VariableSelectorUI(String parent){
		this.parentModelID = parent;
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
		indexMap = new TreeMap();
	}

	private void initLabel() {
		nameLabel = new JLabel(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
		nameLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(nameLabel);
	}
	
	private void initConfigMenu() {
		varList = new JComboBox();
		varList.setVisible(false);
		initValues();
		varList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
			    if(!isUpdate){
			    	JComboBox cb = (JComboBox) evt.getSource();
				    Object item = cb.getSelectedItem();
			    	if (evt.getActionCommand().equals("comboBoxChanged")) {
			    		// Verifies the context. If it's an isolated variable selector then editStateOff behavior is sustained.
			    		if(isIsolated) 
			    			editStateOff((String) item);
			    		if(warningState){
			    			turnWaningStateOFF();
			    		}
			    	} 
			    }
			}
		});
		add(varList);
	}

	private void initValues() {
		String parentID = parentModelID;
		if(parentID.contains("_")) parentID = parentModelID.substring(0, parentModelID.indexOf("_"));
		DataObject component = (DataObject) Services.getService().getModelMapping().get(parentID);
		Function f = (Function) Services.getService().getModelMapping().get(component.getScopeID());
		Vector variables = f.getLocalVariableMap().toVector();
		for(int i = 0; i < variables.size(); i++){
			Variable var = (Variable) Services.getService().getModelMapping().get(variables.get(i));
			String name = (var).getVariableName();
			indexMap.put(var.getUniqueID(), name);
		}
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
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
		private int clickCounter = 0;
		public ExpressionMouseListener(JPanel c){ container = c; }

		public void mouseEntered(MouseEvent e) {
			if(editState){
				setBackground(hoverColor);
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
		
		public void mouseExited(MouseEvent e) {
			if(editState){
				setBackground(bgColor);
				e.getComponent().setCursor(Cursor.getDefaultCursor());
			}
		}
		public void mouseClicked(MouseEvent arg0) {
			if(editState){
				editStateOn();
				varList.requestFocus();
			}
		}
		
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
		
	}
	//END: Mouse listener
	
	//BEGIN: Variable listener methods
	public void addedVariable(String id) { 
		String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
		indexMap.put(id, name);
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
	}
	
	public void changeVariable(String id) { }
	public void removedVariable(String id) { 
		String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
		indexMap.put(id, null);
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
		if(nameLabel.isVisible()||varList.getItemCount()==0){
			if(name.equals(nameLabel.getText())){
				turnWaningStateON();
			}
		}
	}

	public void changeVariableName(String id, String name, String lastName) {
		isUpdate = true;
		indexMap.put(id, name);
		updateVariableList();
		isUpdate = false;
		if(nameLabel.isVisible() && nameLabel.getText().equals(lastName)){
			nameLabel.setText(name);
			nameLabel.revalidate();
			nameLabel.repaint();
			isUpdate = true;
			isUpdate = false;
		}
	}
	
	public void changeVariableValue(String id, String value) { }
	public void changeVariableType(String id, short type) { }
	
	public void variableRestored(String id) { 
		String name = ((Variable) Services.getService().getModelMapping().get(id)).getVariableName();
		indexMap.put(id,name);
		if(nameLabel.getText().equals((name))){
			turnWaningStateOFF();
			isUpdate = true;
			updateVariableList();
			isUpdate = false;
			editStateOff(name);
		}
		isUpdate = true;
		updateVariableList();
		isUpdate = false;
	}
	//END: Variable listener methods
	
	//BEGIN: support methods
	public void editStateOn() {
		varList.setVisible(true);
		nameLabel.setVisible(false);
		drawBorder = false;
		if(getParent() instanceof ExpressionHolderUI)
			((ExpressionHolderUI) getParent()).editStateOn();
		if(!isIsolated) editState = true;
		revalidate();
		repaint();
	}
	
	public void editStateOff(String item) {
		varList.setVisible(false);
		if(item != null && item != "")
			nameLabel.setText(item);
		else
			nameLabel.setText(ResourceBundleIVP.getString("variableSelectorInitialLabel"));
		nameLabel.setVisible(true);
		if(getParent() instanceof ExpressionHolderUI)
			((ExpressionHolderUI) getParent()).editStateOff();
		if(!isIsolated) editState = false;
		revalidate();
		repaint();
	}
	
	private void turnWaningStateON() {
		iconLabel.setVisible(true);
		warningState = true;
		editStateOn();
	}
	
	private void turnWaningStateOFF() {
		iconLabel.setVisible(false);
		warningState = false;
		editStateOff(null);
	}
	
	private void updateVariableList(){
		varList.removeAllItems();
		Object[] keySetArray = indexMap.keySet().toArray();
		int count = 0;
		for(int i = 0; i < keySetArray.length; i++){
			String variableName = (String) indexMap.get(keySetArray[i]);
			if(variableName != null){
				count++;
			}
		}
		isOnlyOneElement = count == 1? true: false;
		for(int i = 0; i < keySetArray.length; i++){
			String variableName = (String) indexMap.get(keySetArray[i]);
			if(variableName != null){
				varList.addItem(variableName);
			}
		}
	}


	public String getScopeID() {
		return scopeModelID;
	}

	public void setScopeID(String scopeID) {
		this.scopeModelID = scopeID;
	}

	public String getCurrentModelID() {
		return currentModelID;
	}

	public void setCurrentModelID(String currentModelID) {
		this.currentModelID = currentModelID;
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

	public boolean isEditState() {
		return editState;
	}

	public void setEditState(boolean editState) {
		this.editState = editState;
	}

	public String getVarListSelectedItem() {
		return (String) varList.getSelectedItem();
	}
	
	public void setIsolationMode(boolean isIso){
		isIsolated = isIso;
	}
	
	public boolean isIsolated(){
		return isIsolated;
	}
}
