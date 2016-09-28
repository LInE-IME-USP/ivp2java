/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * Create a component to iVProg2 commands
 * 
 * @see: 
 * 
 */

package usp.ime.line.ivprog.view.utils;

import java.awt.*;

import javax.swing.JPanel;

import usp.ime.line.ivprog.view.FlatUIColors;

public class RoundedJPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  protected Dimension arcs = new Dimension(5, 5);
  protected static int idCounter = 0;
  private Color borderColor = FlatUIColors.CODE_BORDER_BG;
  private Color bgColor = FlatUIColors.CODE_BG;

  public RoundedJPanel () {
    super();
    setOpaque(false);
    }

  protected void paintComponent (Graphics gr) {
    super.paintComponent(gr);
    Object oldAntialiasing = null;
    if (gr instanceof java.awt.Graphics2D) {
      oldAntialiasing = ((java.awt.Graphics2D) gr).getRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING);
      ((java.awt.Graphics2D) gr).addRenderingHints(new java.awt.RenderingHints(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON));
      }
    java.awt.Rectangle bounds = getBounds();
    gr.setColor(bgColor);
    gr.fillRoundRect(0, 0, bounds.width, bounds.height, arcs.width, arcs.height);
    gr.setColor(borderColor);
    gr.drawRoundRect(0, 0, bounds.width - 1, bounds.height - 1, arcs.width, arcs.height);
    if (gr instanceof java.awt.Graphics2D) {
      ((java.awt.Graphics2D) gr).addRenderingHints(new java.awt.RenderingHints(java.awt.RenderingHints.KEY_ANTIALIASING, oldAntialiasing));
      }
    }

  public void setArcs (Dimension arcs) {
    this.arcs = arcs;
    repaint();
    }

  public void setBackgroundColor (Color bgColor) {
    this.bgColor = bgColor;
    }

  }