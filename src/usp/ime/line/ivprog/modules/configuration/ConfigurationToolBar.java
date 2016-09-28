
/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.modules.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;

//x import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;
import ilm.framework.modules.IlmModuleToolbar;

public class ConfigurationToolBar extends IlmModuleToolbar {

  private JButton button;
  private ConfigurationGUI frame;

  // Called by: usp.ime.line.ivprog.modules.configuration.ConfigurationModule.ConfigurationModule(): _gui = new ConfigurationToolBar();
  public ConfigurationToolBar () {
    button = makeButton("settings", "CONFIGURATIONS", ResourceBundleIVP.getString("configBtn.Tip"), ResourceBundleIVP.getString("configBtn.AltText"));
    try {
      frame = new ConfigurationGUI();
    } catch (Exception e) {
      e.printStackTrace();
      }
    button.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent arg0) {
        frame.setVisible(true);
        Tracking.track("event=CLICK;where=BTN_CONFIGURATION;");
        }
      });
    add(button);
    }

  public void update (Observable arg0, Object arg1) {
    }

  }
