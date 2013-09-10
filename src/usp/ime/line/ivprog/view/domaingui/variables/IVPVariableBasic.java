package usp.ime.line.ivprog.view.domaingui.variables;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.view.components.editinplace.EditInPlace;
import usp.ime.line.ivprog.view.components.editinplace.IValueListener;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;

public class IVPVariableBasic extends RoundedJPanel {

	private JPanel valueContainer;
	private JLabel equalLabel;
	
	private EditInPlace name;
	private EditInPlace value;
	
	private JLabel valueLabel;
	
	
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
		initName();
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
		value = new EditInPlace();
		value.setValue("0");
		value.setValueListener(new IValueListener() {
			@Override
			public void valueChanged(String value) {
				Services.getService().getController().changeVariableInitialValue(id, value);
			}
		});
		add(value);
		
	}

	private void initName(){
		name = new EditInPlace();
		name.setValueListener(new IValueListener() {
			@Override
			public void valueChanged(String value) {
				Services.getService().getController().changeVariableName(id, value);
			}
		});
		add(name);
	}
	
	private void initEqualLabel() {
		equalLabel = new JLabel("=");
		add(equalLabel);
	}
	
	public void setVariableName(String name) {
		this.name.setValue(name);
	}
	public void setVariableValue(String value){
		this.value.setValue(value);
	}
	
	public void setEscope(String escope) {
		escopeID = escope;
	}
	
	public void setID(String uniqueID) {
		id = uniqueID;
	}

	
}
