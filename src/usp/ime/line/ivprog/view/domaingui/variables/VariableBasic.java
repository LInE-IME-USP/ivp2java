package usp.ime.line.ivprog.view.domaingui.variables;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VariableBasic extends JPanel implements MouseListener {
	
	private JPanel valueContainer;
	private JLabel equalLabel;
	private JLabel nameLabel;
	private JTextField nameField;
	private JPanel nameContainer;
	private JPanel optionsContainer;

	public VariableBasic() {
		initialization();
	}

	private void initialization() {
		initLayout();
		initNameContainer();
		initEqualLabel();
		initNameLabel();
		initValueContainer();
		initOptionsContainer();
	}

	private void initNameLabel() {
		nameLabel = new JLabel();
	}
	
	private void initLayout() {
		setOpaque(false);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		setLayout(flowLayout);
	}

	private void initOptionsContainer() {
		optionsContainer = new JPanel();
		add(optionsContainer);
	}

	private void initValueContainer() {
		valueContainer = new JPanel();
		add(valueContainer);
	}

	private void initEqualLabel() {
		equalLabel = new JLabel("=");
		add(equalLabel);
	}

	private void initNameContainer() {
		nameContainer = new JPanel();
		add(nameContainer);
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
