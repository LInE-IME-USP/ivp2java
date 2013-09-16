package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import usp.ime.line.ivprog.view.utils.RoundedJPanel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class IVPCodeBase extends RoundedJPanel{
	
	private JPanel header;
	private JPanel grabPanel;
	private JPanel mainPanel;
	private JLabel grabLabel;

	public IVPCodeBase() {
		initLayout();
		initHeader();
		initGrabArea();
		initMainPanel();
	}

	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setOpaque(true);
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
	}

	private void initGrabArea() {
		grabPanel = new JPanel();
		grabPanel.setOpaque(true);
		grabLabel = new JLabel("");
		grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab1.png")));
		grabLabel.addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent arg0) {
				grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab2.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab1.png")));
			}
			public void mouseClicked(MouseEvent arg0) {	}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		grabPanel.add(grabLabel);
		mainPanel.add(grabPanel, BorderLayout.WEST);
	}

	private void initHeader() {
		header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setOpaque(true);
		add(header, BorderLayout.NORTH);
	}

	private void initLayout() {
		setBackground(new Color(240, 248, 255));
		setLayout(new BorderLayout(0, 0));
	}
	
	protected void addHeader(JComponent head){
		header.add(head, BorderLayout.CENTER);
	}

}
