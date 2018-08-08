package usp.ime.line.ivprog;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.gui.AuthoringGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Font;

public class IlmAuthoringGUI extends AuthoringGUI {
	private JTextPane textPane;

	public IlmAuthoringGUI() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 200));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		textPane = new JTextPane();
		textPane.setFont(new Font("Consolas", Font.PLAIN, 11));
		panel.add(textPane);
		JLabel lblInsiraOsCasos = new JLabel("Insira os casos de testes");
		panel.add(lblInsiraOsCasos, BorderLayout.NORTH);
		JButton btnFecharEdio = new JButton("Fechar edi\u00E7\u00E3o");
		btnFecharEdio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnFecharEdio, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		pack();
	}

	public void update(Observable arg0, Object arg1) {
	}

	protected void initFields() {
	}

	protected String getProposition() {
		return "";
	}

	protected String getAssignmentName() {
		return "";
	}

	protected AssignmentState getInitialState() {
		return null;
	}

	protected AssignmentState getExpectedAnswer() {
		return null;
	}

	protected HashMap getConfig() {
		return null;
	}

	protected HashMap getMetadata() {
		return null;
	}

	public String getTestCases() {
		return textPane.getText();
	}
}
