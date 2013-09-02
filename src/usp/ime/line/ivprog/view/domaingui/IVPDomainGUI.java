package usp.ime.line.ivprog.view.domaingui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.IVPFunctionBody;
import usp.ime.line.ivprog.view.Renderer;

import javax.swing.border.EmptyBorder;

public class IVPDomainGUI extends JPanel implements Observer {
	
	private static final long serialVersionUID = 4725912646391705263L;
	private JPanel workspaceContainer;
	private JTabbedPane tabbedPane;
	private Renderer renderer = null;

	public IVPDomainGUI() {
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout(0, 0));
		initTabbedPane();
		renderer = new Renderer();
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
		System.out.println("Update from model "+model+" "+o);
		if(o instanceof Function){
			updateFunction(renderer.renderFunction((Function) o));
		}
		revalidate();
		repaint();
	}
	
	//update function tabs
	public void addTab(String tabName, IVPFunctionBody function){
		tabbedPane.add(tabName, function);
	}
	
	public void removeTabAtIndex(int index){
		tabbedPane.remove(index);
	}
	
	public void updateFunction(IVPFunctionBody function){
		if(tabbedPane.getTabCount() == 0){
			System.out.println("deveria ter adicionado "+function);
			tabbedPane.add(function.getName(), function);
			return;
		}
		for(int i = 0; i < tabbedPane.getTabCount(); i++){
			if(tabbedPane.getTitleAt(i).equals(function.getName())){
				tabbedPane.remove(i);
				tabbedPane.add(function, i);
			}
		}
	}
	
}
