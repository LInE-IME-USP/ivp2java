/*
 * iLM - interactive Learning Modules in the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 *
 * @description Tool to present button and action to evaluate the activity
 * 
 * @author Danilo L. Dalmon, LOB
 * 
 */

package ilm.framework.modules.operation;

import ilm.framework.modules.IlmModuleToolbar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;

//xx import netscape.javascript.JSObject;
import usp.ime.line.ivprog.Ilm;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AutoCheckingModuleToolbar extends IlmModuleToolbar implements ActionListener {
//public class AutoCheckingModuleToolbar extends IlmModuleToolbar {

  private static final long serialVersionUID = 1L;
  private JButton button;
  //TODO 2016/09/23: private static AutomaticCheckingModule _module;
  private AutomaticCheckingModule module;

  public AutoCheckingModuleToolbar (AutomaticCheckingModule module) {
    //x this._module = module;
    this.module = module;
    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    button = makeButton("autochecking", "AUTOMATIC CHECKING", ResourceBundleIVP.getString("evaluationBtn.Tip"), ResourceBundleIVP.getString("evaluationBtn.AltText"));

    //TODO 2016/09/23: erased to avoid 'static AutomaticCheckingModule _module'
    //x button.addActionListener(new ActionListener () {
    //x   public void actionPerformed (ActionEvent arg0) {
    //x     this._module.getEvaluation(); // showEvaluation();
    //x     //TODO 2016/09/23
    //x     // Framework could not use application methods! Besides, 'track(...)' only prepare POST, what is an LMS action!
    //x     // It uses: HttpUtil.addPostVariable("trackingData", trackingData);
    //x     // Tracking.track("event=CLICK;where=BTN_AUTOMATIC_CHECKING;");
    //x     }
    //x   });
    button.addActionListener(this);

    add(button);
    //x _module.addObserver(this);
    module.addObserver(this);

    }


  //TODO 'static' could generate problems with multiple instance of the iLM: public static void showEvaluation()
  public void getEvaluation () {
    if (Ilm.getInstance() != null) {

      //TODO 2016/09/19 - this was used to present the result in a window (let the LMS take care of that)
      //xx JSObject window = JSObject.getWindow(Ilm.getInstance());
      //xx window.call("getEvaluationCallback", new Object[] { _module.getEvaluation()   });

      //x float eval = _module.getEvaluation();
      float eval = module.getEvaluation();
      if (ilm.framework.IlmProtocol.DEBUG)
        System.out.println("\nilm/framework/modules/operation/AutoCheckingModuleToolbar.java: AutoCheckingModuleToolbar: module.getEvaluation=" + eval);
      }
    else {
      if (ilm.framework.IlmProtocol.DEBUG)
        System.out.println("\nilm/framework/modules/operation/AutoCheckingModuleToolbar.java: AutoCheckingModuleToolbar: module.getEvaluation()");
      //x _module.getEvaluation();
      module.getEvaluation();
      }
    }

  public void update (Observable arg0, Object arg1) {
    button.setEnabled(true);
    // if (_module.hasExpectedAnswer()) { button.setEnabled(true);   } else { button.setEnabled(false); }
    }

  public void actionPerformed (ActionEvent arg0) {
    this.getEvaluation(); // showEvaluation();
    //TODO 2016/09/23
    // Framework could not use application methods! Besides, 'track(...)' only prepare POST, what is an LMS action!
    // It uses: HttpUtil.addPostVariable("trackingData", trackingData);
    // Tracking.track("event=CLICK;where=BTN_AUTOMATIC_CHECKING;");
    }

  }
