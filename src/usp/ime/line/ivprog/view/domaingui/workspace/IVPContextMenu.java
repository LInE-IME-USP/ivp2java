package usp.ime.line.ivprog.view.domaingui.workspace;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.model.IVPConstants;
import usp.ime.line.ivprog.view.utils.IconButtonUI;

public class IVPContextMenu extends JPanel {

	private static final long serialVersionUID = 3814837809047109772L;
	private IVPContainer container = null;
	private JPanel btnPanel;
	private JPanel menuPanel;
	private JPanel buttonsContainer;
	private JButton plusBtn;
	private JButton whileBtn;
	private JButton ifElseBtn;
	private JButton forBtn;
	private JButton writeBtn;

	public IVPContextMenu(IVPContainer c) {
		container = c;
		initialization();
		initPanels();
		initButtons();
	}

	private void initialization() {
		setBorder(new EmptyBorder(2, 2, 2, 2));
		setLayout(new BorderLayout(0, 0));
		setBackground(new Color(210, 245, 215));
	}

	private void initPanels() {
		btnPanel = new JPanel();
		btnPanel.setBackground(new Color(210, 245, 215));
		btnPanel.setLayout(new BorderLayout());
		add(btnPanel, BorderLayout.WEST);
		menuPanel = new JPanel();
		menuPanel.setBackground(new Color(210, 245, 215));
		menuPanel.setLayout(new BorderLayout());
		add(menuPanel, BorderLayout.CENTER);
		buttonsContainer = new JPanel();
		buttonsContainer.setVisible(false);
		buttonsContainer.setBackground(new Color(210, 245, 215));
		buttonsContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.revalidate();
		menuPanel.repaint();
		menuPanel.add(buttonsContainer);
	}

	private void initButtons() {
		initPlusBtn();
		initWhileBtn();
		initIfElseBtn();
		initForBtn();
		initWriteBtn();
	}

	private void initWriteBtn() {
		writeBtn = new JButton("Escreva");
		writeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Services.getService().getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_WRITE);
				hideMenu();
			}
		});
		buttonsContainer.add(writeBtn);
	}

	private void initForBtn() {
		forBtn = new JButton("Faça N vezes");
		forBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Services.getService().getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_FOR);
				hideMenu();
			}
		});
		buttonsContainer.add(forBtn);
	}

	private void initIfElseBtn() {
		ifElseBtn = new JButton("Se/Senão");
		ifElseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Services.getService().getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_IFELSE);
				hideMenu();
			}
		});
		buttonsContainer.add(ifElseBtn);
	}

	private void initWhileBtn() {
		whileBtn = new JButton("Enquanto");
		whileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Services.getService().getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_WHILE);
				hideMenu();
			}
		});
		buttonsContainer.add(whileBtn);
	}

	private void initPlusBtn() {
		plusBtn = new JButton();
		plusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				movePanel(new Point(-menuPanel.getWidth(), 0));
				Runnable r = new Runnable() {
					public void run() {
						int delay = 1;
						buttonsContainer.setVisible(true);
						for (int i = 0; i < menuPanel.getWidth(); i++) {
							try {
								movePanel(new Point(i - menuPanel.getWidth(), 0));
								Thread.sleep(delay);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				};
				Thread t = new Thread(r);
				t.start();
				plusBtn.setEnabled(false);
			}
		});
		plusBtn.setIcon(new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/plus_btn_icon.png")));
		plusBtn.setUI(new IconButtonUI());
		btnPanel.add(plusBtn);
	}

	private void hideMenu() {
		Runnable r = new Runnable() {
			public void run() {
				int delay = 1;
				for (int i = 0; i > -menuPanel.getWidth(); i--) {
					try {
						Thread.sleep(delay);
						movePanel(new Point(i, 0));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				buttonsContainer.setVisible(false);
				plusBtn.setEnabled(true);
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	private void movePanel(final Point p) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buttonsContainer.setLocation(p);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(15, 150, 0));
		java.awt.Rectangle bounds = getBounds();
		for (int i = 0; i < bounds.width; i += 6) {
			g.drawLine(i, 0, i + 3, 0);
			g.drawLine(i + 3, bounds.height - 1, i + 6, bounds.height - 1);
		}
		for (int i = 0; i < bounds.height; i += 6) {
			g.drawLine(0, i, 0, i + 3);
			g.drawLine(bounds.width - 1, i + 3, bounds.width - 1, i + 6);
		}
	}

}
