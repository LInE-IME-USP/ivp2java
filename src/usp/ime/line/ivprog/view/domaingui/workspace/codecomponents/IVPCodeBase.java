package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IVPCodeBase extends RoundedJPanel{
	
	private JPanel header;
	private JPanel mainPanel;
	private JLabel grabLabel;
	private JPanel trashCanPanel;
	private JButton btnLixo;
	private JPanel grabPanel;
	private String containerID; 
	private String id; 

	public IVPCodeBase() {
		initLayout();
		initHeader();
		initMainPanel();
		initTrashCanPanel();
		initTrashBtn();
		initGrabArea();
	}

	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		mainPanel.setOpaque(false);
		mainPanel.setVisible(false);
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
	}

	private void initHeader() {
		header = new JPanel();
		header.setOpaque(false);
		add(header, BorderLayout.NORTH);
		header.setLayout(new BorderLayout(0, 0));
	}

	private void initGrabArea() {
		grabPanel = new JPanel();
		grabPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		grabPanel.setOpaque(false);
		header.add(grabPanel, BorderLayout.WEST);
		grabLabel = new JLabel("");
		grabPanel.add(grabLabel);
		grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab1.png")));
		grabLabel.addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e) {
				grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab2.png")));
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				grabLabel.setIcon(new ImageIcon(IVPCodeBase.class.getResource("/usp/ime/line/resources/icons/grab1.png")));
				e.getComponent().setCursor(Cursor.getDefaultCursor());
			}
			public void mouseClicked(MouseEvent arg0) {	}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
	}

	private void initTrashBtn() {
		btnLixo = new JButton("Lixo");
		btnLixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Services.getService().getController().removeChild(containerID, id);
			}
		});
		trashCanPanel.add(btnLixo);
	}

	private void initTrashCanPanel() {
		trashCanPanel = new JPanel();
		trashCanPanel.setOpaque(false);
		trashCanPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		header.add(trashCanPanel, BorderLayout.EAST);
	}

	private void initLayout() {
		setBackground(new Color(240, 248, 255));
		setLayout(new BorderLayout(0, 0));
	}
	
	protected void addHeader(JComponent head){
		header.add(head, BorderLayout.CENTER);
	}
	
	protected void addMainPanel(JComponent contentPanel){
		if(contentPanel instanceof IVPContainer){
			contentPanel.setPreferredSize(new Dimension(100,35));
		}
		mainPanel.add(contentPanel, BorderLayout.CENTER);
	}
	
	protected void mainPanelSetVisible(boolean b){
		mainPanel.setVisible(b);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	
}
