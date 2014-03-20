package usp.ime.line.ivprog.view.utils;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.model.utils.Services;

public class GripArea extends JPanel {
    private static final long serialVersionUID = 1L;
    protected java.awt.Color  highlightColor   = javax.swing.plaf.metal.MetalLookAndFeel.getControlHighlight();
    protected java.awt.Color  shadowColor      = javax.swing.plaf.metal.MetalLookAndFeel.getControlDarkShadow();
    private String            modelID;
    
    public GripArea(String componentID) {
        setModelID(componentID);
        setMinimumSize(new java.awt.Dimension(12, 0));
        setMaximumSize(new java.awt.Dimension(12, Integer.MAX_VALUE));
        setPreferredSize(new java.awt.Dimension(12, 0));
        setOpaque(false);
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
            }
            
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
            public void mousePressed(MouseEvent arg0) {
            }
            
            public void mouseReleased(MouseEvent arg0) {
            }
        });
        addMouseListener(Services.getService().getML());
        addMouseMotionListener((MouseMotionListener) Services.getService().getML());
        try {
            ImageIcon icon = new ImageIcon(GripArea.class.getResource("/usp/ime/line/resources/icons/up_down_caret.png"));
            JLabel label = new JLabel("", icon, JLabel.CENTER);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getModelID() {
        return modelID;
    }
    
    public void setModelID(String modelID) {
        this.modelID = modelID;
    }
}
