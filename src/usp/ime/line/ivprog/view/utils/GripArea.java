package usp.ime.line.ivprog.view.utils;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class GripArea extends JPanel {

    private static final long serialVersionUID = 1L;
    protected java.awt.Color highlightColor = javax.swing.plaf.metal.MetalLookAndFeel
            .getControlHighlight();
    protected java.awt.Color shadowColor = javax.swing.plaf.metal.MetalLookAndFeel
            .getControlDarkShadow();

    public GripArea() {
        setMinimumSize(new java.awt.Dimension(12, 0));
        setMaximumSize(new java.awt.Dimension(12, Integer.MAX_VALUE));
        setPreferredSize(new java.awt.Dimension(12, 0));
        setOpaque(false);
        addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent e) { 
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); 
			}
			public void mouseExited(MouseEvent e) {
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        java.awt.Dimension size = getSize();
        g.setColor(highlightColor);
        for (int x = 0; x < size.width; x += 4) {
            for (int y = 2; y < size.height; y += 4) {
                g.drawLine(x, y, x, y);
                g.drawLine(x + 2, y + 2, x + 2, y + 2);
            }
        }
        g.setColor(shadowColor);
        for (int x = 0; x < size.width; x += 4) {
            for (int y = 2; y < size.height; y += 4) {
                g.drawLine(x + 1, y + 1, x + 1, y + 1);
                g.drawLine(x + 3, y + 3, x + 3, y + 3);
            }
        }
    }
}
