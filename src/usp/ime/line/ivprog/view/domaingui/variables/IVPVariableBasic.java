package usp.ime.line.ivprog.view.domaingui.variables;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import sun.swing.BakedArrayList;
import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Color;

public class IVPVariableBasic extends RoundedJPanel {

	private JPanel valueContainer;
	private JLabel equalLabel;
	private JLabel nameLabel;
	private JLabel valueLabel;
	private JTextField nameField;
	private JPanel nameContainer;
	private JPanel optionsContainer;
	private JButton configBtn;
	private JButton excludeBtn;
	private String escopeID;
	private String id;
	
	public static Color BACKGROUND_COLOR = new Color(204, 255, 204);

	public IVPVariableBasic() {
		setBackground(BACKGROUND_COLOR);
		initialization();
	}

	private void initialization() {
		initLayout();
		initNameContainer();
		initNameLabel();
		initNameField();
		initEqualLabel();
		initValueContainer();
		initOptionsContainer();
		
		initBtns();
	}

	private void initLayout() {
		setOpaque(false);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setAlignment(FlowLayout.LEFT);
		setLayout(flowLayout);
	}

	private void initOptionsContainer() {
		optionsContainer = new JPanel();
		optionsContainer.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) optionsContainer.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(optionsContainer);
	}

	private void initBtns() {
		initConfigBtn();
		initDeleteBtn();
	}

	private void initDeleteBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().deleteVariable(escopeID, id);
			}
		};
		action.putValue(
				Action.SMALL_ICON,
				new ImageIcon(
						IVPVariableBasic.class
								.getResource("/usp/ime/line/resources/icons/varDelete2.png")));
		action.putValue(Action.SHORT_DESCRIPTION,
				"Adiciona um novo parâmetro à função:" + "Principal");
		excludeBtn = new JButton(action);
		excludeBtn.setUI(new IconButtonUI());
		optionsContainer.add(excludeBtn);
	}

	private void initConfigBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		action.putValue(
				Action.SMALL_ICON,
				new ImageIcon(
						IVPVariableBasic.class
								.getResource("/usp/ime/line/resources/icons/varConfig2.png")));
		action.putValue(Action.SHORT_DESCRIPTION,
				"Adiciona um novo parâmetro à função:" + "Principal");
		configBtn = new JButton(action);
		configBtn.setUI(new IconButtonUI());
		optionsContainer.add(configBtn);
	}

	private void initValueContainer() {
		valueContainer = new JPanel();
		valueLabel = new JLabel("0");
		
		valueContainer.add(valueLabel);
		
		//valueContainer.setOpaque(false);
		
		add(valueContainer);
	}

	private void initEqualLabel() {
		equalLabel = new JLabel("=");
		add(equalLabel);
	}

	private void initNameContainer() {
		nameContainer = new JPanel();
		//nameContainer.setOpaque(false);
		add(nameContainer);
	}
	private void initNameLabel(){
		nameLabel = new JLabel();
		nameLabel.addMouseListener(new VariableMouseListener());
		nameContainer.add(nameLabel);
	}
	private void initNameField(){
		nameField = new JTextField(5);
		nameField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				nameContainer.setVisible(true);
				nameField.setVisible(false);
				
				// TODO validar
				Services.getService().getController().changeVariableName(id, nameField.getText());
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.println("FOCUS");
			}
		});
		nameField.setVisible(false);
		add(nameField);
		
	}

	public void setVariableName(String name) {
		nameLabel.setText(name);
	}
	
	public void setEscope(String escope) {
		escopeID = escope;
	}
	
	public void setID(String uniqueID) {
		id = uniqueID;
	}

	private class VariableMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent arg0) {
			nameContainer.setBackground(Color.yellow);
		}
		public void mouseExited(MouseEvent arg0) {
			nameContainer.setBackground(BACKGROUND_COLOR);
		}
		public void mousePressed(MouseEvent arg0) {
			System.out.println("PRESSED");
		}
		public void mouseReleased(MouseEvent e) {
			System.out.println("released");
			if(e.getSource().equals(nameLabel)){
				System.out.println("Chegou");
				//nameContainer.removeAll();
				//nameContainer.add(nameField);
				//nameContainer.revalidate();
				//nameContainer.repaint();
				nameField.setVisible(true);
				nameContainer.setVisible(false);
				nameField.requestFocus();
			}
		}	
	}
	

}
