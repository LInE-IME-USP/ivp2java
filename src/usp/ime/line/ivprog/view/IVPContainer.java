package usp.ime.line.ivprog.view;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
import java.util.HashMap;
import java.util.Vector;

public class IVPContainer extends JPanel {
	
	private static final long serialVersionUID = 8071308601441583992L;
	private boolean isInternal = false;
	private CodeComposite container;
	
	public IVPContainer(boolean isInternalContainer, CodeComposite c) {
		setBackground(new Color(255, 240, 250));
		container = c;
		isInternal = isInternalContainer;
		initialization();
	}

	private void initialization() {
		setLayout(new GridBagLayout());
		if (isInternal)
            setPreferredSize(new Dimension(10, 30));
		addChild(new IVPContextMenu(this));
	}
	
	public void addChild(JComponent c){
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 2, 2, 2);
        gbc.gridy = getComponentCount() - 1;
        add(c, gbc);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = getComponentCount() - 1;
        Component strut = Box.createVerticalStrut(1);
        add(strut, gbc);
        revalidate();
        repaint();
	}

	public CodeComposite getCodeComposite() {
		return container;
	}

}
