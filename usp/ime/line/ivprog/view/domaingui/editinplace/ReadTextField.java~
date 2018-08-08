package usp.ime.line.ivprog.view.domaingui.editinplace;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;

import java.awt.ComponentOrientation;

public class ReadTextField extends JPanel implements KeyListener {
	private JTextField textField;
	public static int PATTERN_VARIABLE_NAME = 0;
	public static int PATTERN_VARIABLE_VALUE_DOUBLE = 1;
	public static int PATTERN_VARIABLE_VALUE_INTEGER = 2;
	public static int PATTERN_VARIABLE_VALUE_STRING = 3;
	private IValueListener valueListener;
	private int currentPattern = 0;
	private String[] patternsTyping = { "^[a-zA-Z_][a-zA-Z0-9_]*$", "^[-]?[0-9]*.[0-9]*$", "^[-]?[0-9]*$", ".*" };
	private String[] patterns = { "^[a-zA-Z_][a-zA-Z0-9_]*$", "^[-]?[0-9]*.[0-9]*$", "^[-]?[0-9]*$", ".*" };
	private Color bgColor = FlatUIColors.MAIN_BG;

	public ReadTextField() {
		textField = new JTextField();
		textField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(textField);
		textField.setColumns(25);
		initTextFieldConfig();
		initInputMap();
		setBackground(bgColor);
	}

	public void keyTyped(KeyEvent e) {
		// enter é 13... verificar pq esta funfando com 10
		if (((int) e.getKeyChar()) == 10 || ((int) e.getKeyChar()) == 13) {
			valueListener.valueChanged(textField.getText());
			return;
		}
		String value = textField.getText();
		if (textField.getSelectionStart() == 0) {
			value = e.getKeyChar() + value;
		} else if (textField.getSelectionStart() == value.length()) {
			value = value + e.getKeyChar();
		} else {
			value = value.substring(0, textField.getSelectionStart()) + e.getKeyChar() + value.substring(textField.getSelectionEnd());
		}
		if (!value.matches(patternsTyping[currentPattern])) {
			int c = ((int) e.getKeyChar());
			if ((c != 8) && (c != 127)) {
				getToolkit().beep();
				e.consume();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	private void initInputMap() {
		AbstractAction editDone = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				Tracking.getInstance().track("event=FOCUSLOST;where=EDIT_DONE_READ_PANEL;");
				textField.setFocusable(false);
				textField.setFocusable(true);
			}
		};
		textField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), editDone);
		textField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), editDone);
		textField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_TAB), editDone);
	}

	private void initTextFieldConfig() {
		textField.addKeyListener(this);
		textField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				String value = textField.getText();
				if (!value.matches(patterns[currentPattern])) {
					textField.setBorder(BorderFactory.createLineBorder(Color.red));
				} else {
					valueListener.valueChanged(textField.getText());
				}
			}

			public void focusGained(FocusEvent arg0) {
			}
		});
		initInputMap();
	}

	public void setValue(String value) {
		textField.setText(value);
	}

	public int getCurrentPattern() {
		return currentPattern;
	}

	public void setCurrentPattern(int currentPattern) {
		this.currentPattern = currentPattern;
	}

	public void resetTextField() {
		textField.setText("");
	}

	public void setValueListener(IValueListener v) {
		valueListener = v;
	}
}
