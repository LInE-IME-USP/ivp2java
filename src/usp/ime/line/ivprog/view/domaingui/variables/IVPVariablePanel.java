package usp.ime.line.ivprog.view.domaingui.variables;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class IVPVariablePanel extends JPanel {

	private static final long serialVersionUID = -2214975678822644250L;
	
	public IVPVariablePanel (){
		setBackground(new Color(240, 240, 255));
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(15,15,150));
		java.awt.Rectangle bounds = getBounds();
		for(int i = 0; i < bounds.width; i+=6){
			g.drawLine(i, 0, i+3, 0);
			g.drawLine(i+3, bounds.height-1, i+6, bounds.height-1);
		}
		for(int i = 0; i < bounds.height; i+=6){
			g.drawLine(0, i, 0, i+3);
			g.drawLine(bounds.width-1,i+3 , bounds.width-1,i+6 );
		}
	}
	
}
