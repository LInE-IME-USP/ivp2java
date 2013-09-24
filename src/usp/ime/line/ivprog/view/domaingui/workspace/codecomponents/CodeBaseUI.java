package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.utils.GripArea;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class CodeBaseUI extends RoundedJPanel {
	
	private String parentID;
	private String thisID;
	private JPanel compositePanel;
	private JPanel gripArea;
	private JPanel trashCanPanel;
	
	public CodeBaseUI(){
		setLayout(new BorderLayout());
		initGripArea();
		initCompositePanel();
		initTrashCan();
	}

	private void initTrashCan() {
		trashCanPanel = new JPanel(new BorderLayout());
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().removeChild(parentID, thisID);
			}
		};
		action.putValue(Action.SMALL_ICON,new ImageIcon(CodeBaseUI.class.getResource("/usp/ime/line/resources/icons/trash16x16.png")));
		action.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("removeComponent"));
		JButton btn = new JButton(action);
		btn.setUI(new IconButtonUI());
		trashCanPanel.setBorder(new EmptyBorder(3,3,3,3));
		trashCanPanel.setOpaque(false);
		trashCanPanel.add(btn, BorderLayout.NORTH);
		add(trashCanPanel, BorderLayout.EAST);
	}

	private void initCompositePanel() {
		compositePanel = new JPanel(new BorderLayout());
		compositePanel.setOpaque(false);
		add(compositePanel, BorderLayout.CENTER);
	}

	private void initGripArea() {
		GripArea grip = new GripArea();
		gripArea = new JPanel();
        gripArea.setLayout(new BorderLayout());
        gripArea.add(grip, BorderLayout.CENTER);
        gripArea.setBorder(new EmptyBorder(0, 2, 2, 2));
        gripArea.add(grip, BorderLayout.CENTER);
        gripArea.setOpaque(false);
        add(gripArea, BorderLayout.WEST);
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getThisID() {
		return thisID;
	}

	public void setThisID(String thisID) {
		this.thisID = thisID;
	}

}
