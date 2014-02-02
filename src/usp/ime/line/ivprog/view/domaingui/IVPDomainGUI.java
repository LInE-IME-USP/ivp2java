package usp.ime.line.ivprog.view.domaingui;

import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Color;
import java.awt.Font;

public class IVPDomainGUI extends DomainGUI implements IFunctionListener {

	private static final long serialVersionUID = 4725912646391705263L;
	private JPanel workspaceContainer;
	private JTabbedPane tabbedPane;
	private JPanel consolePanel;
	private JPanel playAndConsolePanel;
	private RoundedJPanel consoleContainer;
	private IVPConsoleUI consoleField;
	private JButton btnPlay;
	private JLabel lblNewLabel;
	private Component verticalStrut;

	public IVPDomainGUI() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout(0, 0));
		Services.getService().getController().getProgram().addFunctionListener(this);
		initTabbedPane();
	}

	private void initTabbedPane() {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setDividerSize(3);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		workspaceContainer = new JPanel();
		splitPane.setLeftComponent(workspaceContainer);
		workspaceContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		workspaceContainer.setLayout(new BorderLayout(0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		workspaceContainer.add(tabbedPane, BorderLayout.CENTER);
		
		consolePanel = new JPanel();
		consolePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		splitPane.setRightComponent(consolePanel);
		consolePanel.setLayout(new BorderLayout(0, 0));
		
		playAndConsolePanel = new JPanel();
		playAndConsolePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		consolePanel.add(playAndConsolePanel, BorderLayout.WEST);
		playAndConsolePanel.setLayout(new BoxLayout(playAndConsolePanel, BoxLayout.Y_AXIS));
		
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				//Services.getService().getController().addParameter(scopeID);
			}
		};
		action.putValue(Action.SMALL_ICON,new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/play.png")));
		action.putValue(Action.SHORT_DESCRIPTION,"Executa a função principal.");
		
		lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(IVPDomainGUI.class.getResource("/usp/ime/line/resources/icons/console.png")));
		playAndConsolePanel.add(lblNewLabel);
		
		verticalStrut = Box.createVerticalStrut(10);
		playAndConsolePanel.add(verticalStrut);
		
		btnPlay = new JButton(action);
		btnPlay.setUI(new IconButtonUI());
		btnPlay.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						Services.getService().getController().playCode();
					}
				}
		);
		playAndConsolePanel.add(btnPlay);
		
		consoleContainer = new RoundedJPanel();
		consoleContainer.setBackgroundColor(FlatUIColors.CONSOLE_COLOR);
		consoleContainer.setArcs(new Dimension(10,10));
		consoleContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		consolePanel.add(consoleContainer, BorderLayout.CENTER);
		consoleContainer.setLayout(new BorderLayout(0, 0));

		consoleField = new IVPConsoleUI();
		consoleContainer.add(consoleField);
	}

	public void update(Observable model, Object o) {
		//Not going to be used anymore
		//We adopted other pattern
	}

	// update function tabs
	public void addTab(String tabName, FunctionBodyUI function) {
		tabbedPane.add(tabName, function);
	}

	public void removeTabAtIndex(int index) {
		tabbedPane.remove(index);
	}

	public void updateFunction(FunctionBodyUI function) {
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
		updateFunction((FunctionBodyUI) Services.getService().getRenderer().paint(id));
	}

	public void initDomainActionList(DomainModel model) {
	}

}
