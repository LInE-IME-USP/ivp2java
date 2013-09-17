package usp.ime.line.ivprog.view.domaingui.workspace;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Vector;

public class IVPContainer extends JPanel implements ICodeListener {

	private static final long serialVersionUID = 8071308601441583992L;
	private boolean isInternal = false;
	private String containerID;
	private IVPContextMenu menu;

	public IVPContainer(boolean isInternalContainer, String contID) {
		setBackground(new Color(255, 240, 250));
		Services.getService().getController().addComponentListener(this, contID);
		containerID = contID;
		isInternal = isInternalContainer;
		initialization();
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				IVPContainer.this.requestFocus();
			}
			
			public void mouseClicked(MouseEvent arg0) {
				IVPContainer.this.requestFocus();
			}
			
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
		});
	}

	private void initialization() {
		setLayout(new GridBagLayout());
		if (isInternal)
			setPreferredSize(new Dimension(10, 30));
		menu = new IVPContextMenu(this);
		addChild(menu);
	}

	public void addChild(JComponent c) {
		Component[] components = getComponents();
		removeAll();
		addComponent(c, 0);
		for(int i = 0; i <= components.length - 2; i++){
			addComponent((JComponent) components[i], i+1);
		}
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 2, 2, 2);
		gbc.gridy = components.length;
		add(menu, gbc);
		gbc.gridy = components.length+1;
		Component strut = Box.createVerticalStrut(1);
		add(strut, gbc);
		revalidate();
		repaint();
	}

	private void addComponent(JComponent c, int i) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 2, 2, 2);
		gbc.gridy = i;		
		add(c, gbc);
	}

	public String getCodeComposite() {
		return containerID;
	}

	public void childAdded(String childID) {
		JComponent c = Services.getService().getRenderer().paint(childID);
		Services.getService().getViewMapping().put(childID, c);
		addChild(c);
	}

}
