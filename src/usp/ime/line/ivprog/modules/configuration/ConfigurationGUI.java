package usp.ime.line.ivprog.modules.configuration;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.IVPMouseListener;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ConfigurationGUI extends JFrame {
	private JPanel contentPane;
	private JRadioButton rdBtnCnP;
	private JRadioButton rdBtnDnD;
	private Vector lastConfigState;
	private Vector configState;
	private JPanel panel;
	private JLabel lblInteration;

	public ConfigurationGUI() {
		initLayoutAndPanel();
		initLabelInteraction();
		initDNDRdBtn();
		initCNPRdBtn();
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void initCNPRdBtn() {
		rdBtnCnP = new JRadioButton(ResourceBundleIVP.getString("rdBtnCnP.text"));
		rdBtnCnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdBtnDnD.setSelected(false);
				dispose();
				Services.getService().getController().changeInteractionProtocol(IVPMouseListener.INTERACTION_PROTOCOL_CNP);
				Tracking.getInstance().track("event=CLICK;where=RDBTN_CNP;");
			}
		});
		rdBtnCnP.setOpaque(false);
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_1.gridx = 2;
		gbc_rdbtnNewRadioButton_1.gridy = 0;
		panel.add(rdBtnCnP, gbc_rdbtnNewRadioButton_1);
	}

	private void initDNDRdBtn() {
		rdBtnDnD = new JRadioButton(ResourceBundleIVP.getString("rdBtnDnD.text"));
		rdBtnDnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdBtnCnP.setSelected(false);
				Tracking.getInstance().track("event=CLICK;where=RDBTN_DND;");
				dispose();
				Services.getService().getController().changeInteractionProtocol(IVPMouseListener.INTERACTION_PROTOCOL_DND);
			}
		});
		rdBtnDnD.setSelected(true);
		rdBtnDnD.setOpaque(false);
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.gridx = 1;
		gbc_rdbtnNewRadioButton.gridy = 0;
		panel.add(rdBtnDnD, gbc_rdbtnNewRadioButton);
	}

	private void initLabelInteraction() {
		lblInteration = new JLabel(ResourceBundleIVP.getString("lblInteration.text"));
		lblInteration.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblInteraoPadro = new GridBagConstraints();
		gbc_lblInteraoPadro.insets = new Insets(0, 0, 5, 5);
		gbc_lblInteraoPadro.anchor = GridBagConstraints.EAST;
		gbc_lblInteraoPadro.gridx = 0;
		gbc_lblInteraoPadro.gridy = 0;
		panel.add(lblInteration, gbc_lblInteraoPadro);
	}

	private void initLayoutAndPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		panel.setBackground(FlatUIColors.MAIN_BG);
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
	}
}
