package ilm.framework.modules;

import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.IconButtonUI;

public abstract class IlmModuleToolbar extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    
    public IlmModuleToolbar() {
        setBackground(FlatUIColors.MAIN_BG);
    }
    
    protected JButton makeButton(String imageName, String actionCommand, String toolTipText, String altText) {
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        try {
            button.setIcon(new ImageIcon(IlmModuleToolbar.class.getResource("/usp/ime/line/resources/" + imageName + ".png"), altText));
        } catch (Exception e) {
            System.err.println("Error: image './usp/ime/line/resources/" + imageName + ".png' is missing: ilm/framework/modules/IlmModuleToolbar.java");
        }
        button.setUI(new IconButtonUI());
        return button;
    }
}
