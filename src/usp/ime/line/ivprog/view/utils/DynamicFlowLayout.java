package usp.ime.line.ivprog.view.utils;

public class DynamicFlowLayout extends java.awt.FlowLayout {

    private static final long  serialVersionUID = 1L;
    private java.awt.Dimension lastPreferredSize;
    private java.awt.Component anchorComponent;
    private int                anchorConstant   = 0;
    private Class              anchorClass;

    public DynamicFlowLayout(int align, java.awt.Component anchor, Class anchorClass, int anchorConstant) {
        super(align);
        this.anchorComponent = anchor;
        this.anchorClass = anchorClass;
        this.anchorConstant = anchorConstant;
    }

    public void layoutContainer(java.awt.Container target) {
        synchronized (target.getTreeLock()) {
            java.awt.Insets insets = target.getInsets();
            int hgap = getHgap();
            int vgap = getVgap();
            if (lastPreferredSize == null) {
                lastPreferredSize = preferredLayoutSize(target);
            }
            int maxwidth = lastPreferredSize.width;
            int nmembers = target.getComponentCount();
            int x = 0, y = insets.top + vgap;
            int rowh = 0, start = 0;
            boolean ltr = target.getComponentOrientation().isLeftToRight();
            for (int i = 0; i < nmembers; i++) {
                java.awt.Component m = target.getComponent(i);
                if (m.isVisible()) {
                    java.awt.Dimension d = m.getPreferredSize();
                    m.setSize(d.width, d.height);
                    if ((x == 0) || ((x + d.width) <= maxwidth)) {
                        if (x > 0) {
                            x += hgap;
                        }
                        x += d.width;
                        rowh = Math.max(rowh, d.height);
                    } else {
                        moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, i, ltr);
                        x = d.width;
                        y += vgap + rowh;
                        rowh = d.height;
                        start = i;
                    }
                }
            }
            moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, nmembers, ltr);
        }
    }

    private void moveComponents(java.awt.Container target, int x, int y, int width, int height, int rowStart, int rowEnd, boolean ltr) {
        synchronized (target.getTreeLock()) {
            switch (getAlignment()) {
            case LEFT:
                x += ltr ? 0 : width;
                break;
            case CENTER:
                x += width / 2;
                break;
            case RIGHT:
                x += ltr ? width : 0;
                break;
            case LEADING:
                break;
            case TRAILING:
                x += width;
                break;
            }
            for (int i = rowStart; i < rowEnd; i++) {
                java.awt.Component m = target.getComponent(i);
                if (target.isVisible()) {
                    if (ltr) {
                        m.setLocation(x, y + (height - m.getHeight()) / 2);
                    } else {
                        m.setLocation(target.getWidth() - x - m.getWidth(), y + (height - m.getHeight()) / 2);
                    }
                    x += m.getWidth() + getHgap();
                }
            }
        }
    }

    public java.awt.Dimension preferredLayoutSize(java.awt.Container target) {
        java.awt.Insets insets = target.getInsets();
        int hgap = getHgap();
        int vgap = getVgap();
        if (anchorComponent == null) {
            anchorComponent = getAnchor(target);
        }
        int maxwidth = 0;
        if (anchorComponent == null) {
            maxwidth = (target.getWidth() - (insets.left + insets.right + hgap * 2));
        } else {
            maxwidth = anchorComponent.getWidth() - (insets.left + insets.right + hgap * 2) - anchorConstant;
        }
        int nmembers = target.getComponentCount();
        int x = 0, y = insets.top + vgap;
        int rowh = 0;
        if (maxwidth < 0) {
            maxwidth = 0;
            for (int i = 0; i < nmembers; i++) {
                java.awt.Component m = target.getComponent(i);
                if (m.isVisible()) {
                    java.awt.Dimension d = m.getPreferredSize();
                    y = Math.max(y, d.height);
                    if (d.width > 0 && d.height > 0) {
                        if (maxwidth > 0) {
                            maxwidth += hgap;
                        }
                        maxwidth += d.width;
                    }
                }
            }
        } else {
            target.getComponentOrientation().isLeftToRight();
            for (int i = 0; i < nmembers; i++) {
                java.awt.Component m = target.getComponent(i);
                if (m.isVisible()) {
                    java.awt.Dimension d = m.getPreferredSize();
                    if ((x == 0) || ((x + d.width) <= maxwidth)) {
                        if (x > 0) {
                            x += hgap;
                        }
                        x += d.width;
                        rowh = Math.max(rowh, d.height);
                    } else {
                        x = d.width;
                        y += vgap + rowh;
                        rowh = d.height;
                    }
                }
            }
        }
        lastPreferredSize = new java.awt.Dimension(maxwidth, y + rowh + vgap);
        return lastPreferredSize;
    }

    public java.awt.Dimension minimumLayoutSize(java.awt.Container target) {
        return preferredLayoutSize(target);
    }

    private java.awt.Component getAnchor(java.awt.Component current) {
        if (current == null || anchorClass.isAssignableFrom(current.getClass())) {
            return current;
        } else {
            return getAnchor(current.getParent());
        }
    }
}
