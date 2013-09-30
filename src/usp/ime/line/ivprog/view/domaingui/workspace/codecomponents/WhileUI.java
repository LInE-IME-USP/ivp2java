package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.BlueishButtonUI;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class WhileUI extends CodeBaseUI {
	
	private JPanel contentPanel;
	private JPanel header;
	private IVPContainer container;
	private JPanel expressionHolder;
	private JLabel codeBlockName;
	private JButton expandBtnUP;
	private JButton expandBtnDOWN;
	private Icon up;
	private Icon down;
	
	private static Color bgColor = new Color(189, 195, 199);
	
	public WhileUI(String id) {
		setThisID(id);
		initContentPanel();
		initExpandButtonIcon();
		initHeader();
		initContainer();
		addContentPanel(contentPanel);
		setBackground(bgColor);
	}

	private void initContainer() {
		container = new IVPContainer(true, getThisID());
		container.setContainerBackground(bgColor);
		container.setVisible(false);
		contentPanel.add(container, BorderLayout.CENTER);
	}

	private void initExpandButtonIcon() {
		 up = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_up.png"));
	     down = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_down.png"));
	 }

	private void initContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setOpaque(false);
	}
	
	private void initHeader() {
		header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setOpaque(false);
		contentPanel.add(header, BorderLayout.NORTH);
		initExpandBtnUP();
		initExpandBtnDOWN();
		initCodeBlockLabel();
	}
	
	private void initExpandBtnUP() {
		expandBtnUP = new JButton();
		expandBtnUP.setIcon(up);
		expandBtnUP.setUI(new IconButtonUI());
		expandBtnUP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				expandedActions();
			}
		});
		expandBtnUP.setVisible(false);
		header.add(expandBtnUP);
	}
	
	private void initExpandBtnDOWN() {
		expandBtnDOWN = new JButton();
		expandBtnDOWN.setIcon(down);
		expandBtnDOWN.setUI(new IconButtonUI());
		expandBtnDOWN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				notExpandedAction();
			}
		});
		header.add(expandBtnDOWN);
	}

	protected void notExpandedAction() {
		container.setVisible(true);
		expandBtnUP.setVisible(true);
		expandBtnDOWN.setVisible(false);
		revalidate();
		repaint();
	}

	protected void expandedActions() {
		container.setVisible(false);
		expandBtnUP.setVisible(false);
		expandBtnDOWN.setVisible(true);
		revalidate();
		repaint();
	}

	private void initCodeBlockLabel() {
		codeBlockName = new JLabel(ResourceBundleIVP.getString("whileTitle"));
		header.add(codeBlockName);
	}

}
