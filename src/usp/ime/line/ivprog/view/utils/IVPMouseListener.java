package usp.ime.line.ivprog.view.utils;

import ilm.framework.domain.DomainGUI;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IfElse;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPMouseListener extends MouseAdapter {
    
    private String             currentProtocol             = INTERACTION_PROTOCOL_DND;
    public static final String INTERACTION_PROTOCOL_DND    = "drag&drop";
    public static final String INTERACTION_PROTOCOL_CNP    = "catch&paste";
    private boolean            isHolding                   = false;
    private String             holdingComponent            = "";
    private JComponent         lastEnteredComponent        = null;
    private int                lastYOnLastEnteredComponent = 0;
    
    // --------------------------------------------------------------------------------
    // MouseListener
    public void mouseClicked(MouseEvent event) {
        if (currentProtocol.equals(INTERACTION_PROTOCOL_CNP)) {
            if (!isHolding) {
                getComponent(event.getSource());
            } else {
                if (event.getSource() instanceof IVPContainer) {
                    dropComponent((IVPContainer) event.getSource(), event.getY());
                }
            }
        }
    }
    
    public void mouseEntered(MouseEvent event) {
        if (currentProtocol.equals(INTERACTION_PROTOCOL_DND)) {
            if (event.getSource() instanceof IVPContainer) {
                lastEnteredComponent = (JComponent) event.getComponent();
                lastYOnLastEnteredComponent = event.getY();
                event.consume();
            }
        }
    }
    
    public void mouseReleased(MouseEvent event) {
        if (currentProtocol.equals(INTERACTION_PROTOCOL_DND)) {
            if (lastEnteredComponent instanceof IVPContainer && isHolding) {
                dropComponent((IVPContainer) lastEnteredComponent, lastYOnLastEnteredComponent);
            }
        }
    }
    
    public void mouseExited(MouseEvent event) {
    }
    
    public void mousePressed(MouseEvent event) {
    }
    
    // --------------------------------------------------------------------------------
    // Motion Listener
    public void mouseDragged(MouseEvent event) {
        if (currentProtocol.equals(INTERACTION_PROTOCOL_DND)) {
            if (!isHolding) {
                getComponent(event.getSource());
            }
        }
    }
    
    public void mouseMoved(MouseEvent event) {
    }
    
    // --------------------------------------------------------------------------------------- end
    public String getProtocol() {
        return currentProtocol;
    }
    
    public void setProtocol(String protocol) {
        this.currentProtocol = protocol;
    }
    
    private void getComponent(Object object) {
        if (object instanceof GripArea) {
            isHolding = true;
            holdingComponent = ((GripArea) object).getModelID();
            Services.getService().getController().changeCursor(DragSource.DefaultMoveDrop);
        }
    }
    
    private void dropComponent(IVPContainer target, int dropY) {
        String origin = ((DataObject) Services.getService().getModelMapping().get(holdingComponent)).getParentID();
        String destiny = target.getCodeComposite();
        DataObject holdingParent = ((DataObject) Services.getService().getModelMapping().get(origin));
        String originContext = (holdingParent instanceof IfElse) ? ((IfElse) holdingParent).getChildContext(holdingComponent) : "";
        String destinyContext = ("".equals(target.getContext())) ? "" : target.getContext();
        JComponent holdingJComponent = (JComponent) Services.getService().getViewMapping().get(holdingComponent);
        if (holdingJComponent.isAncestorOf(target)) {
            Services.getService().getController().printError(ResourceBundleIVP.getString("Error.dropCodeInsideItSelf"));
        } else {
            Services.getService().getController().moveChild(holdingComponent, origin, target.getCodeComposite(), originContext, destinyContext, target.getDropIndex(dropY, holdingJComponent));
        }
        holdingComponent = "";
        isHolding = false;
        lastEnteredComponent = null;
        lastYOnLastEnteredComponent = 0;
        Services.getService().getController().changeCursor(Cursor.DEFAULT_CURSOR);
    }
}
