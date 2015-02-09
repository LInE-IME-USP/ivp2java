package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.For;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Color;
import java.awt.Font;

public class ForUI extends CodeBaseUI implements ICodeListener {
	private JPanel contentPanel;
	private JPanel header;
	private IVPContainer container;
	private JButton expandBtnUP;
	private JButton expandBtnDOWN;
	private Icon up;
	private Icon down;
	private Icon dot;
	private Icon ellipsis;
	private Icon mode2;
	private String context;
	private JLabel codeBlockName;
	private JLabel timesLabel;
	private JLabel timesIncrementing;
	private JLabel oneByOne;
	private JLabel fromLbl;
	private JLabel upToLbl;
	private JLabel stepLbl;
	private JLabel lastParLbl;
	private JLabel codeBlockName_using;
	private ExpressionFieldUI lowerBoundField;
	private ExpressionFieldUI upperBoundField;
	private ExpressionFieldUI incrementField;
	private ExpressionFieldUI indexField;
	private ExpressionFieldUI mode_1and2_upperBound;
	private JButton btnLvl3;
	private JButton btnLvl2;
	private JButton btnLvl1;
	private int forMode = For.FOR_MODE_1;

	public ForUI(String id) {
		super(id);
		setModelID(id);
		initContentPanel();
		initExpandButtonIcon();
		initHeader();
		initExpressionHolder();
		initContainer();
		addContentPanel(contentPanel);
		setBackground(FlatUIColors.MAIN_BG);
		Services.getService().getController().addComponentListener(this, id);
	}

	private void initContainer() {
		container = new IVPContainer(true, getModelID(), "");
		container.setContainerBackground(FlatUIColors.MAIN_BG);
		container.setVisible(false);
		contentPanel.add(container, BorderLayout.CENTER);
	}

	private void initExpandButtonIcon() {
		up = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_up.png"));
		down = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_down.png"));
		dot = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/dot.png"));
		ellipsis = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/ellipsis.png"));
		mode2 = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/mode2.png"));
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
		initExpression();
		initFieldsAndLabels();
		initCodeBlockLabel();
	}

	private void initExpressionHolder() {
	}

	private void initExpression() {
	}

	private void initExpandBtnUP() {
		expandBtnUP = new JButton();
		expandBtnUP.setIcon(up);
		expandBtnUP.setUI(new IconButtonUI());
		expandBtnUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tracking.getInstance().track("event=CLICK;where=BTN_EXPAND_CODE;");
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
		expandBtnDOWN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tracking.getInstance().track("event=CLICK;where=BTN_CONTRACT_CODE;");
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

	private void initFieldsAndLabels() {
		String index;
		index = ((For) Services.getService().getModelMapping().get(getModelID())).getIndexExpression();
		initIndexField(index);
		codeBlockName = new JLabel(ResourceBundleIVP.getString("ForUI.for.text"));
		timesLabel = new JLabel(ResourceBundleIVP.getString("ForUI.times.text"));
		fromLbl = new JLabel(ResourceBundleIVP.getString("ForUI.from.text"));
		fromLbl.setVisible(false);
		initLowerBound();
		upToLbl = new JLabel(ResourceBundleIVP.getString("ForUI.upTo.text"));
		upToLbl.setVisible(false);
		initMode1UppderBound();
		initUpperBound();
		stepLbl = new JLabel(ResourceBundleIVP.getString("ForUI.step.text"));
		stepLbl.setVisible(false);
		initIncrementField();
		lastParLbl = new JLabel(ResourceBundleIVP.getString("ForUI.step2.text"));
		lastParLbl.setVisible(false);
		timesIncrementing = new JLabel(ResourceBundleIVP.getString("ForUI.timesIncrementing.text"));
		timesIncrementing.setVisible(false);
		oneByOne = new JLabel(ResourceBundleIVP.getString("ForUI.oneByOne.text"));
		oneByOne.setVisible(false);
		codeBlockName_using = new JLabel(ResourceBundleIVP.getString("ForUI.forUsing.text"));
		codeBlockName_using.setVisible(false);
		initBtns();
	}

	private void initBtns() {
		btnLvl3 = new JButton(ellipsis);
		btnLvl3.setUI(new IconButtonUI());
		btnLvl3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tracking.getInstance().track("event=CLICK;where=BTN_FOR_BTNLVL3;");
				level3Action();
			}
		});
		btnLvl3.setVisible(false);
		btnLvl2 = new JButton(mode2);
		btnLvl2.setUI(new IconButtonUI());
		btnLvl2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tracking.getInstance().track("event=CLICK;where=BTN_FOR_BTNLVL2;");
				level2Action();
			}
		});
		btnLvl2.setVisible(true);
		btnLvl1 = new JButton(dot);
		btnLvl1.setUI(new IconButtonUI());
		btnLvl1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tracking.getInstance().track("event=CLICK;where=BTN_FOR_BTNLVL1;");
				level1Action();
			}
		});
		btnLvl1.setVisible(false);
	}

	private void initMode1UppderBound() {
		mode_1and2_upperBound = new ExpressionFieldUI(this.getModelID(), this.getModelScope());
		mode_1and2_upperBound.setBlocked(false);
		mode_1and2_upperBound.setVisible(true);
		mode_1and2_upperBound.setHoldingType(Expression.EXPRESSION_INTEGER);
		mode_1and2_upperBound.setForHeader(true);
		mode_1and2_upperBound.hideMenu(true);
		mode_1and2_upperBound.setForContext("mode1_forUpperBound");
	}

	private void initIncrementField() {
		incrementField = new ExpressionFieldUI(getModelID(), this.getModelScope());
		incrementField.setBlocked(false);
		incrementField.setHoldingType(Expression.EXPRESSION_INTEGER);
		incrementField.setVisible(false);
		incrementField.setForHeader(true);
		incrementField.setForContext("forIncrement");
	}

	private void initUpperBound() {
		upperBoundField = new ExpressionFieldUI(this.getModelID(), this.getModelScope());
		upperBoundField.setBlocked(false);
		upperBoundField.setVisible(false);
		upperBoundField.setHoldingType(Expression.EXPRESSION_INTEGER);
		upperBoundField.setForHeader(true);
		upperBoundField.setForContext("forUpperBound");
	}

	private void initLowerBound() {
		lowerBoundField = new ExpressionFieldUI(getModelID(), this.getModelScope());
		lowerBoundField.setBlocked(false);
		lowerBoundField.setHoldingType(Expression.EXPRESSION_INTEGER);
		lowerBoundField.setVisible(false);
		lowerBoundField.setForHeader(true);
		lowerBoundField.setForContext("forLowerBound");
	}

	private void initIndexField(String index) {
		indexField = new ExpressionFieldUI(getModelID(), this.getModelScope());
		indexField.setBlocked(false);
		indexField.setHoldingType(Expression.EXPRESSION_INTEGER);
		indexField.setHolderContent(Services.getService().getRenderer().paint(index));
		indexField.setVisible(false);
		indexField.setForHeader(true);
		indexField.hideMenu(true);
		indexField.setForContext("forIndex");
	}

	private void level3Action() {
		codeBlockName.setVisible(false);
		codeBlockName_using.setVisible(true);
		timesLabel.setVisible(false);
		indexField.setVisible(true);
		oneByOne.setVisible(false);
		mode_1and2_upperBound.setVisible(false);
		timesIncrementing.setVisible(false);
		fromLbl.setVisible(true);
		lowerBoundField.setVisible(true);
		upToLbl.setVisible(true);
		upperBoundField.setVisible(true);
		stepLbl.setVisible(true);
		incrementField.setVisible(true);
		lastParLbl.setVisible(true);
		btnLvl1.setVisible(true);
		btnLvl2.setVisible(false);
		btnLvl3.setVisible(false);
		Services.getService().getController().changeForMode(For.FOR_MODE_3, getModelID());
		forMode = For.FOR_MODE_3;
	}

	private void level2Action() {
		codeBlockName.setVisible(true);
		codeBlockName_using.setVisible(false);
		timesLabel.setVisible(false);
		indexField.setVisible(true);
		oneByOne.setVisible(true);
		mode_1and2_upperBound.setVisible(true);
		timesIncrementing.setVisible(true);
		fromLbl.setVisible(false);
		lowerBoundField.setVisible(false);
		upToLbl.setVisible(false);
		upperBoundField.setVisible(false);
		stepLbl.setVisible(false);
		incrementField.setVisible(false);
		lastParLbl.setVisible(false);
		btnLvl1.setVisible(false);
		btnLvl2.setVisible(false);
		btnLvl3.setVisible(true);
		Services.getService().getController().changeForMode(For.FOR_MODE_2, getModelID());
		forMode = For.FOR_MODE_2;
	}

	private void level1Action() {
		codeBlockName.setVisible(true);
		codeBlockName_using.setVisible(false);
		timesLabel.setVisible(true);
		mode_1and2_upperBound.setVisible(true);
		fromLbl.setVisible(false);
		indexField.setVisible(false);
		lowerBoundField.setVisible(false);
		upToLbl.setVisible(false);
		upperBoundField.setVisible(false);
		stepLbl.setVisible(false);
		incrementField.setVisible(false);
		lastParLbl.setVisible(false);
		btnLvl1.setVisible(false);
		btnLvl2.setVisible(true);
		btnLvl3.setVisible(false);
		Services.getService().getController().changeForMode(For.FOR_MODE_1, getModelID());
		forMode = For.FOR_MODE_1;
	}

	private void initCodeBlockLabel() {
		header.add(codeBlockName_using);
		header.add(codeBlockName);
		header.add(mode_1and2_upperBound);
		header.add(timesIncrementing);
		header.add(indexField);
		header.add(oneByOne);
		header.add(fromLbl);
		header.add(lowerBoundField);
		header.add(upToLbl);
		header.add(upperBoundField);
		header.add(stepLbl);
		header.add(incrementField);
		header.add(lastParLbl);
		header.add(timesLabel);
		header.add(btnLvl3);
		header.add(btnLvl2);
		header.add(btnLvl1);
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

	public void addChild(String childID, String context) {
		container.addChild(childID);
	}

	public void childRemoved(String childID, String context) {
		container.childRemoved(childID);
	}

	public void restoreChild(String childID, int index, String context) {
		container.restoreChild(childID, index);
	}

	public void moveChild(String childID, String context, int index) {
		container.moveChild(childID, index);
	}

	public boolean isContentSet() {
		boolean isCSet = true;
		if (forMode == For.FOR_MODE_1) {
			if (!mode_1and2_upperBound.isContentSet()) {
				isCSet = false;
			}
		} else if (forMode == For.FOR_MODE_2) {
			if (!mode_1and2_upperBound.isContentSet()) {
				isCSet = false;
			}
			if (!indexField.isContentSet()) {
				if (isCSet) {
					isCSet = false;
				}
			}
		} else {
			if (!indexField.isContentSet()) {
				if (isCSet) {
					isCSet = false;
				}
			}
			if (!lowerBoundField.isContentSet()) {
				if (isCSet) {
					isCSet = false;
				}
			}
			if (!upperBoundField.isContentSet()) {
				if (isCSet) {
					isCSet = false;
				}
			}
			if (!incrementField.isContentSet()) {
				if (isCSet) {
					isCSet = false;
				}
			}
		}
		if (!container.isContentSet()) {
			if (isCSet) {
				isCSet = false;
			}
		}
		return isCSet;
	}

	public void lockDownCode() {
		indexField.setEdition(false);
		incrementField.setEdition(false);
		upperBoundField.setEdition(false);
		lowerBoundField.setEdition(false);
		mode_1and2_upperBound.setEdition(false);
		container.lockCodeDown();
	}
}
