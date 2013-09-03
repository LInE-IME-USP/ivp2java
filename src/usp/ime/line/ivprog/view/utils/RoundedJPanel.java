package usp.ime.line.ivprog.view.utils;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    protected Dimension arcs = new Dimension(5, 5);
    protected static int idCounter = 0;
    private Color borderColor = java.awt.Color.DARK_GRAY;

    public RoundedJPanel() {
        super();
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Object oldAntialiasing = null;
        if (g instanceof java.awt.Graphics2D) {
            oldAntialiasing = ((java.awt.Graphics2D) g)
                    .getRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING);
            ((java.awt.Graphics2D) g).addRenderingHints(new java.awt.RenderingHints(
                    java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON));
        }
        java.awt.Rectangle bounds = getBounds();
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, bounds.width, bounds.height, arcs.width, arcs.height);
        g.setColor(borderColor);
        g.drawRoundRect(0, 0, bounds.width - 1, bounds.height - 1, arcs.width, arcs.height);
        if (g instanceof java.awt.Graphics2D) {
            ((java.awt.Graphics2D) g).addRenderingHints(new java.awt.RenderingHints(
                    java.awt.RenderingHints.KEY_ANTIALIASING, oldAntialiasing));
        }
    }

    public void setArcs(Dimension arcs) {
        this.arcs = arcs;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
}