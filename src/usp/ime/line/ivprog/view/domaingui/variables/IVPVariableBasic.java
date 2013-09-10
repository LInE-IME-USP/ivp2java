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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Color;

public class IVPVariableBasic extends RoundedJPanel implements MouseListener {

	private JPanel valueContainer;
	private JLabel equalLabel;
	private JLabel nameLabel;
	private JTextField nameField;
	private JPanel nameContainer;
	private JPanel optionsContainer;
	private JButton configBtn;
	private JButton excludeBtn;
	private String escopeID;
	private String id;

	public IVPVariableBasic() {
		setBackground(new Color(204, 255, 204));
		initialization();
	}

	private void initialization() {
		initLayout();
		initNameContainer();
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
				Services.getService().controller().deleteVariable(escopeID, id);
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
		valueContainer.setOpaque(false);
		add(valueContainer);
	}

	private void initEqualLabel() {
		equalLabel = new JLabel("=");
		add(equalLabel);
	}

	private void initNameContainer() {
		nameContainer = new JPanel();
		nameContainer.setOpaque(false);
		add(nameContainer);
	}

	public void setVariableName(String name) {
		nameLabel = new JLabel(name);
		nameContainer.add(nameLabel);
	}
	
	public void setEscope(String escope) {
		escopeID = escope;
	}
	
	public void setID(String uniqueID) {
		id = uniqueID;
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
