package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.BlueishButtonUI;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.JToolBar;

public class WhileUI extends CodeBaseUI {
	
	private JPanel contentPanel;
	private JPanel header;
	private IVPContainer container;
	private ExpressionField expressionField;
	private JLabel codeBlockName;
	private JButton expandBtnUP;
	private JButton expandBtnDOWN;
	private Icon up;
	private Icon down;
	private String context;
	
	private static Color bgColor = new Color(189, 195, 199);
	private BooleanOperationUI booleanOperationUI;
	
	public WhileUI(String id) {
		setModelID(id);
		initContentPanel();
		initExpandButtonIcon();
		initHeader();
		initExpressionHolder();
		initContainer();
		addContentPanel(contentPanel);
		setBackground(bgColor);
	}

	

	private void initContainer() {
		container = new IVPContainer(true, getModelID());
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
		initExpression();
	}
	
	private void initExpressionHolder() {
		
	}

	private void initExpression() {
		String condition = ((While)Services.getService().getModelMapping().get(getModelID())).getCondition();
		booleanOperationUI = new BooleanOperationUI(getModelID(), getModelScope(), condition);
		expressionField = new ExpressionField(this.getModelID(), this.getModelScope());
		expressionField.setHolderContent(booleanOperationUI);
		expressionField.setComparison(true);
		header.add(expressionField);
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
	
	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

}
