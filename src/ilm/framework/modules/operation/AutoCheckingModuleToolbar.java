package ilm.framework.modules.operation;

import ilm.framework.modules.IlmModuleToolbar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import netscape.javascript.JSObject;
import usp.ime.line.ivprog.Ilm;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AutoCheckingModuleToolbar extends IlmModuleToolbar {
    
    private static final long       serialVersionUID = 1L;
    private JButton                 button;
    private static AutomaticCheckingModule _module;
    
    public AutoCheckingModuleToolbar(AutomaticCheckingModule module) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        button = makeButton("autochecking", "AUTOMATIC CHECKING", ResourceBundleIVP.getString("evaluationBtn.Tip"), ResourceBundleIVP.getString("evaluationBtn.AltText"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                showEvaluation();
                    
                
                
                
                Tracking.getInstance().track("event=CLICK;where=BTN_AUTOMATIC_CHECKING;");
            }
        });
        add(button);
        _module = module;
        _module.addObserver(this);
    }
    
    public static void showEvaluation() {
        if(Ilm.getInstance()!=null){
            JSObject window = JSObject.getWindow(Ilm.getInstance());
            window.call("getEvaluationCallback", new Object[]{_module.getEvaluation()});    
        }else{
            _module.getEvaluation();
        }
    }
    
    public void update(Observable arg0, Object arg1) {
        button.setEnabled(true);
        /*
        if (_module.hasExpectedAnswer()) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
        */
    }
}
