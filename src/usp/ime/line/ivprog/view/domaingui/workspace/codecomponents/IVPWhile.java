package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.BlueishButtonUI;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IVPWhile extends CodeBaseUI {
	
	private JPanel header;
	private JLabel componentName;
	private JButton expandBtn;
	private boolean isExpanded = false;
	
	public IVPWhile() {
		initHeader();
		initExpandButton();
		initLabels();
	}
	
	private void initHeader(){
		header = new JPanel();
		header.setOpaque(false);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void initExpandButton() {
		expandBtn = new JButton("+");
		expandBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isExpanded){
					expandBtn.setText("+");
				} else {
					expandBtn.setText("-");
				}
				isExpanded = !isExpanded;				
			}
		});
		expandBtn.setUI(new BlueishButtonUI());
		header.add(expandBtn);
	}
	
	private void initLabels(){
		componentName = new JLabel(ResourceBundleIVP.getString("whileTitle"));
		header.add(componentName);
	}
	
}
