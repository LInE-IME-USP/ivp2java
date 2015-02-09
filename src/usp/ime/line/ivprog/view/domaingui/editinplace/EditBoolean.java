package usp.ime.line.ivprog.view.domaingui.editinplace;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IDomainObjectUI;

import java.awt.FlowLayout;

public class EditBoolean extends JPanel implements IDomainObjectUI {
	private JLabel nameLabel;
	private JPanel nameContainer;
	private JTextField nameField;
	private IValueListener valueListener;
	private boolean value = true;
	private String currentModelID;
	private String parentModelID;
	private String scopeModelID;
	private String context;
	private Color BACKGROUND_COLOR = FlatUIColors.MAIN_BG;

	public EditBoolean() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		initNameContainer();
		initNameLabel();
		initNameField();
	}

	public EditBoolean(Color bg) {
		BACKGROUND_COLOR = bg;
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
		nameField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				nameContainer.setVisible(true);
				nameField.setVisible(false);
				if (valueListener != null) {
					valueListener.valueChanged(nameField.getText());
				}
			}

			public void focusGained(FocusEvent arg0) {
			}
		});
		nameField.setVisible(false);
		initInputMap();
		add(nameField);
	}

	private void initInputMap() {
		AbstractAction editDone = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				Tracking.getInstance().track("event=FOCUSLOST;where=EDIT_BOOLEAN;");
				nameField.setFocusable(false);
				nameField.setFocusable(true);
			}
		};
		nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), editDone);
		nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), editDone);
		nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_TAB), editDone);
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
				// nameField.setVisible(true);
				// nameContainer.setVisible(false);
				// nameField.requestFocus();
				value = !value;
				valueListener.valueChanged(value + "");
				if (value) {
					nameLabel.setText("Verdadeiro");
				} else {
					nameLabel.setText("Falso");
				}
			}
		}
	}

	public void setValue(String name) {
		if (value) {
			nameLabel.setText("Verdadeiro");
		} else {
			nameLabel.setText("Falso");
		}
		revalidate();
		repaint();
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

	public boolean isContentSet() {
		return true;
	}

	public void lockDownCode() {
	}
}
