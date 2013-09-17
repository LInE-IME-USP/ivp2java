package usp.ime.line.ivprog.view.domaingui;

import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JTabbedPane;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.domainaction.NewVariable;
import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPFunctionBody;

import javax.swing.border.EmptyBorder;

public class IVPDomainGUI extends DomainGUI implements IFunctionListener {

	private static final long serialVersionUID = 4725912646391705263L;
	private JPanel workspaceContainer;
	private JTabbedPane tabbedPane;

	public IVPDomainGUI() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout(0, 0));
		Services.getService().getController().getProgram().addFunctionListener(this);
		initTabbedPane();
	}

	private void initTabbedPane() {
		workspaceContainer = new JPanel();
		workspaceContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(workspaceContainer, BorderLayout.CENTER);
		workspaceContainer.setLayout(new BorderLayout(0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		workspaceContainer.add(tabbedPane, BorderLayout.CENTER);
	}

	public void update(Observable model, Object o) {
		//Not going to be used anymore
	}

	// update function tabs
	public void addTab(String tabName, IVPFunctionBody function) {
		tabbedPane.add(tabName, function);
	}

	public void removeTabAtIndex(int index) {
		tabbedPane.remove(index);
	}

	public void updateFunction(IVPFunctionBody function) {
		if (tabbedPane.getTabCount() == 0) {
			tabbedPane.add(function.getName(), function);
			return;
		}
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (tabbedPane.getTitleAt(i).equals(function.getName())) {
				tabbedPane.remove(i);
				tabbedPane.add(function, i);
			}
		}
	}

	protected void initDomainGUI() {

	}

	public Vector getSelectedObjects() {
		return null;
	}

	public void functionCreated(String id) {
		updateFunction((IVPFunctionBody) Services.getService().getRenderer().paint(id));
	}

}
