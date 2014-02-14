package usp.ime.line.ivprog.view.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;
import ilm.framework.modules.IlmModuleToolbar;

public class ConfigurationToolBar extends IlmModuleToolbar {
    private JButton          button;
    private ConfigurationGUI frame;
    
    public ConfigurationToolBar() {
        button = makeButton("settings", "CONFIGURATIONS", ResourceBundleIVP.getString("configBtn.Tip"), ResourceBundleIVP.getString("configBtn.AltText"));
        try {
            frame = new ConfigurationGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(true);
            }
        });
        add(button);
    }
    
    public void update(Observable arg0, Object arg1) {
    }
}
