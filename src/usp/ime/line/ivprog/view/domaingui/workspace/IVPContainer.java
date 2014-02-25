package usp.ime.line.ivprog.view.domaingui.workspace;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IDomainObjectUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class IVPContainer extends JPanel {
    private static final long serialVersionUID = 1L;
    private Vector            children;
    private boolean           isInternalCanvas = false;
    private boolean           isInternal       = false;
    private String            codeCompositeID;
    private IVPContextMenu    menu;
    private String            context;
    
    public IVPContainer(boolean isInternal, String codeCompositeID, String context) {
        setBorder(new EmptyBorder(5, 0, 0, 0));
        isInternalCanvas = isInternal;
        children = new Vector();
        this.context = context;
        this.codeCompositeID = codeCompositeID;
        initLayout();
        initialization();
        addMouseListener(Services.getService().getML());
        addMouseMotionListener(Services.getService().getML());
    }
    
    private void initialization() {
        addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent arg0) {
                IVPContainer.this.requestFocus();
            }
            
            public void mouseClicked(MouseEvent arg0) {
                IVPContainer.this.requestFocus();
            }
            
            public void mousePressed(MouseEvent arg0) {
            }
            
            public void mouseExited(MouseEvent arg0) {
            }
            
            public void mouseEntered(MouseEvent arg0) {
            }
        });
        menu = new IVPContextMenu(this, this.context);
        children.add(menu);
        relayout();
    }
    
    private void initLayout() {
        setLayout(new GridBagLayout());
        setBackground(FlatUIColors.MAIN_BG);
        if (isInternalCanvas)
            setPreferredSize(new Dimension(10, 30));
    }
    
    public void relayout() {
        int row = 0;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        removeAll();
        gbc.gridy = row++;
        gbc.insets = new Insets(4, 3, 2, 5);
        initCanvasHeight();
        children.remove(menu);
        children.add(menu);
        for (int i_i = 0; i_i < children.size(); i_i++) {
            gbc.gridy = row++;
            JComponent c = (JComponent) children.get(i_i);
            add(c, gbc);
        }
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row++;
        Component strut = Box.createVerticalStrut(1);
        add(strut, gbc);
        revalidate();
        repaint();
    }
    
    private void initCanvasHeight() {
        if (children.size() == 0) {
            setPreferredSize(new Dimension(10, 25));
        } else {
            setPreferredSize(null);
        }
    }
    
    public void removeChild(String childID) {
        JComponent child = (JComponent) Services.getService().getViewMapping().get(childID);
        children.remove(child);
        relayout();
    }
    
    public Vector getChildren() {
        return children;
    }
    
    public void setChildren(Vector childrenArray) {
        this.children = childrenArray;
        relayout();
    }
    
    public void dropChild(JComponent child, int dropY) {
        Vector childrenList = children;
        int yLocDroppedPanel = dropY;
        HashMap yLocPanels = new HashMap();
        for (int i = 0; i < childrenList.size(); i++) {
            yLocPanels.put(new Integer(((JPanel) childrenList.get(i)).getY()), childrenList.get(i));
        }
        yLocPanels.put(new Integer(yLocDroppedPanel), child);
        Vector sortableYValues = new Vector();
        sortableYValues.addAll(yLocPanels.keySet());
        Collections.sort(sortableYValues);
        Vector orderedPanels = new Vector();
        for (int i = 0; i < sortableYValues.size(); i++) {
            orderedPanels.add(yLocPanels.get(sortableYValues.get(i)));
        }
        childrenList.clear();
        childrenList.addAll(orderedPanels);
        childrenList.add(childrenList.size(), menu);
        relayout();
    }
    
    public boolean isInternalCanvas() {
        return isInternalCanvas;
    }
    
    public String getCodeComposite() {
        return codeCompositeID;
    }
    
    public void setContainerBackground(Color bgColor) {
        setBackground(FlatUIColors.MAIN_BG);
        revalidate();
        repaint();
    }
    
    // Listener methods
    public void addChild(String childID) {
        JComponent child = Services.getService().getRenderer().paint(childID);
        children.add(children.size() - 1, child);
        relayout();
    }
    
    public void childRemoved(String childID) {
        JComponent c = (JComponent) Services.getService().getViewMapping().get(childID);
        children.remove(c);
        relayout();
    }
    
    public void moveChild(String childID, int toIndex) {
        JComponent c = (JComponent) Services.getService().getViewMapping().get(childID);
        int lastIndex = children.indexOf(c);
        if (toIndex >= lastIndex) {
            children.add(toIndex, c);
            children.remove(lastIndex);
        } else {
            children.remove(c);
            children.add(toIndex, c);
        }
        relayout();
    }
    
    public void restoreChild(String childID, int index) {
        JComponent child = (JComponent) Services.getService().getViewMapping().get(childID);
        if (children.contains(child)) {
            int ind = children.indexOf(child);
            if (index >= ind) {
                children.add(index, child);
                if (ind != -1)
                    children.remove(ind);
            } else {
                children.remove(child);
                children.add(index, child);
            }
        } else {
            if (index != -1)
                children.add(index, child);
            else
                children.add(child);
        }
        relayout();
    }
    
    public int getDropIndex(int dropY, JComponent c) {
        children.remove(menu);
        int index;
        for (index = 0; index < children.size(); index++) {
            if (dropY < ((JPanel) children.get(index)).getY()) {
                if (index > 0) {
                    if (c.equals(children.get(index - 1))) {
                        return index - 1;
                    }
                }
                return index;
            }
        }
        if (index > 0) {
            if (c.equals(children.get(index - 1))) {
                return index - 1;
            }
        }
        children.add(menu);
        return index;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public boolean isContentSet() {
        boolean isCSet = true;
        for (int i = 0; i < children.size() - 1; i++) {
            if (!((IDomainObjectUI) children.get(i)).isContentSet()) {
                if (isCSet) {
                    isCSet = false;
                }
            }
        }
        return isCSet;
    }
    
    public void lockCodeDown() {
        for (int i = 0; i < children.size() - 1; i++) {
            ((IDomainObjectUI) children.get(i)).lockDownCode();
        }
    }
}
