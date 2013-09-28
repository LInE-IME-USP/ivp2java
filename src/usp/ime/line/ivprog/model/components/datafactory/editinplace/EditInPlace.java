package usp.ime.line.ivprog.model.components.datafactory.editinplace;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.listeners.IValueListener;

public class EditInPlace extends JPanel implements KeyListener {

	private JLabel nameLabel;
	private JPanel nameContainer;
	private JTextField nameField;

	private IValueListener valueListener;
	
	public static int PATTERN_VARIABLE_NAME = 0;
	public static int PATTERN_VARIABLE_VALUE_DOUBLE = 1;
	
	private int currentPattern = 0;
	
	private String[] patternsTyping = {
			"^[a-zA-Z_][a-zA-Z0-9_]*$",
			"^[0-9]*$"
	};
	private String[] patterns = {
			"^[a-zA-Z_][a-zA-Z0-9_]*$",
			"\\b[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?\\b"
	};
	//private String pattern = "^[a-zA-Z_][a-zA-Z0-9_]*$";

	public static Color BACKGROUND_COLOR = new Color(204, 255, 204);

	public EditInPlace() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		initNameContainer();
		initNameLabel();
		initNameField();
	}

	private void initNameContainer() {
		nameContainer = new JPanel();
		nameContainer.setBackground(BACKGROUND_COLOR);
		add(nameContainer);
	}

	private void initNameLabel() {
		nameLabel = new JLabel("teste");
		nameLabel.addMouseListener(new VariableMouseListener());
		nameContainer.add(nameLabel);
	}

	private void initNameField() {
		nameField = new JTextField(5);
		nameField.addKeyListener(this);
		nameField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				String value = nameField.getText();
				if(value.matches(patterns[currentPattern])){
					nameContainer.setVisible(true);
					nameField.setVisible(false);
					if (valueListener != null) {
						valueListener.valueChanged(nameField.getText());
					}
					nameField.setBorder(BorderFactory.createEtchedBorder());
				}else{
					nameField.setBorder(BorderFactory.createLineBorder(Color.red));
				}
			}

			public void focusGained(FocusEvent arg0) {
				System.out.println("FOCUS");
			}
		});
		nameField.setVisible(false);
		initInputMap();
		add(nameField);
	}

	private void initInputMap() {
		AbstractAction editDone = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				nameField.setFocusable(false);
				nameField.setFocusable(true);
			}
		};
		nameField.getInputMap().put(
				KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), editDone);
		nameField.getInputMap().put(
				KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), editDone);
		nameField.getInputMap().put(
				KeyStroke.getKeyStroke((char) KeyEvent.VK_TAB), editDone);
	}

	public void setValueListener(IValueListener listener) {
		valueListener = listener;
	}

	private class VariableMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent arg0) {
			nameContainer.setBackground(Color.yellow);
		}

		public void mouseExited(MouseEvent arg0) {
			nameContainer.setBackground(BACKGROUND_COLOR);
		}

		public void mousePressed(MouseEvent arg0) {
			
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getSource().equals(nameLabel)) {
				nameField.setVisible(true);
				nameContainer.setVisible(false);
				nameField.requestFocus();
			}
		}
	}

	public void setValue(String name) {
		nameField.setText(name);
		nameLabel.setText(name);
		revalidate();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(e);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		String value = nameField.getText();
		if(nameField.getSelectionStart()==0){
			value = e.getKeyChar()+value;
		}else if(nameField.getSelectionStart()==value.length()){
			value = value + e.getKeyChar();
		}else{
			value = value.substring(0,nameField.getSelectionStart())+e.getKeyChar()+value.substring(nameField.getSelectionEnd());
		}
		
		if (!value.matches(patternsTyping[currentPattern])) {
			getToolkit().beep();
			e.consume();
		}
	}

	public boolean isValidValue(KeyEvent e) {
		return false;
	}

	public int getCurrentPattern() {
		return currentPattern;
	}

	public void setCurrentPattern(int currentPattern) {
		this.currentPattern = currentPattern;
	}
	
	

}
