package usp.ime.line.ivprog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.view.IVPMenu;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;

public class PrincipalParaTeste extends JFrame {

	private static final long serialVersionUID = 530321876111645014L;
	private JPanel contentPane;
	private IVPProgram model = null;
	private IVPMenu menu = null;
	private IVPDomainGUI domainGUI;
	
	public static void main(String[] args) {
		PrincipalParaTeste frame = new PrincipalParaTeste();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public PrincipalParaTeste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model = new IVPProgram();
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		menu = new IVPMenu();
		domainGUI = new IVPDomainGUI();
		Services.getService().controller().setGui(domainGUI);
		Services.getService().controller().setProgram(model);
		Services.getService().controller().initializeModel();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(size.width/2 - 400, size.height/2 - 300);
		contentPane.add(domainGUI, BorderLayout.CENTER);
		contentPane.add(menu, BorderLayout.WEST);
		setSize(new Dimension(800,600));
		setContentPane(contentPane);
	
	}

}
