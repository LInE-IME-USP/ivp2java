package usp.ime.line.ivprog.model.components.datafactory.editinplace;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.listeners.IValueListener;

import java.awt.FlowLayout;

public class EditInPlace extends JPanel {
	private JLabel nameLabel;
	private JPanel nameContainer;
	private JTextField nameField;

	private IValueListener valueListener;
	
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
	
	private void initNameLabel(){
		nameLabel = new JLabel("teste");
		nameLabel.addMouseListener(new VariableMouseListener());
		nameContainer.add(nameLabel);
	}
	
	private void initNameField(){
		nameField = new JTextField(5);
		nameField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				nameContainer.setVisible(true);
				nameField.setVisible(false);
				
				if(valueListener!=null){
					valueListener.valueChanged(nameField.getText());
				}
			}
			
			public void focusGained(FocusEvent arg0) {
				System.out.println("FOCUS");
			}
		});
		nameField.setVisible(false);
		add(nameField);
		
	}
	
	public void setValueListener(IValueListener listener){
		valueListener = listener;
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
			if(e.getSource().equals(nameLabel)){
				nameField.setVisible(true);
				nameContainer.setVisible(false);
				nameField.requestFocus();
			}
		}	
	}
	public void setValue(String name){
		nameField.setText(name);
		nameLabel.setText(name);
		revalidate();
		repaint();
	}
}
