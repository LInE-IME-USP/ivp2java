package usp.ime.line.ivprog.view;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.l2fprod.common.demo.ButtonBarMain;
import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.misc.IconPackagerButtonBarUI;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

public class IVPMenu extends JPanel {

	private static final long serialVersionUID = 4550155655079034404L;
	private JButtonBar toolbar;
	private ButtonGroup group;
	private JToggleButton play;
	private JToggleButton construct;
	private JPanel domainMenu;
	private JTaskPane taskPane;
	private JTaskPaneGroup taskPaneGroup;

	public IVPMenu() {
		setLayout(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 0));
		toolbar = new JButtonBar(JButtonBar.HORIZONTAL);
		toolbar.setUI(new IconPackagerButtonBarUI());
		toolbar.setPreferredSize(new Dimension(200, 75));
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		group = new ButtonGroup();
		add(toolbar, BorderLayout.NORTH);
		initDomainMenu();
		initButtons();
	}

	private void initDomainMenu() {
		domainMenu = new JPanel();
		domainMenu.setPreferredSize(new Dimension(200, 0));
		domainMenu.setBackground(Color.black);
		add(domainMenu, BorderLayout.CENTER);
		domainMenu.setLayout(new BorderLayout(0, 0));
		{
			taskPane = new JTaskPane();
			domainMenu.add(taskPane);
		}
		{
			taskPaneGroup = new JTaskPaneGroup();
			taskPane.add(taskPaneGroup);
		}
	}

	private void initButtons() {
		initPlayButton();
		initConstructButton();
		construct.setSelected(true);
	}

	private void initConstructButton() {
		Action action = new AbstractAction(
				ResourceBundleIVP.getString("constructMenu"),
				new ImageIcon(
						IVPMenu.class
								.getResource("/usp/ime/line/resources/icons/construction32x32.png"))) {
			public void actionPerformed(ActionEvent e) {
				Services.getService().controller()
						.showConstructionEnvironment();
			}
		};
		construct = new JToggleButton(action);
		toolbar.add(construct);
		group.add(construct);
	}

	private void initPlayButton() {
		Action action = new AbstractAction(
				ResourceBundleIVP.getString("playMenu"),
				new ImageIcon(
						IVPMenu.class
								.getResource("/usp/ime/line/resources/icons/play32x32.png"))) {
			public void actionPerformed(ActionEvent e) {
				Services.getService().controller().showExecutionEnvironment();
			}
		};
		play = new JToggleButton(action);
		toolbar.add(play);
		group.add(play);
	}

}
