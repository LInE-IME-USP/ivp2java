package usp.ime.line.ivprog.view.domaingui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.IVPRenderer;

import javax.swing.border.EmptyBorder;

public class IVPDomainGUI extends JPanel implements Observer {

	private static final long serialVersionUID = 4725912646391705263L;
	private JPanel workspaceContainer;
	private JTabbedPane tabbedPane;

	public IVPDomainGUI() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout(0, 0));
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
		Object domainObject = Services.getService().getRenderer().paint(o);
		if (domainObject instanceof IVPFunctionBody) {
			updateFunction((IVPFunctionBody) domainObject);
		}
		revalidate();
		repaint();
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

}
