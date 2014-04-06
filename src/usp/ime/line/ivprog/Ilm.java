/*
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * 
 */
package usp.ime.line.ivprog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.ZipFile;

import netscape.javascript.JSObject;
import ilm.framework.IlmProtocol;
import ilm.framework.SystemControl;
import ilm.framework.gui.IlmBaseGUI;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.xml.bind.DatatypeConverter;

import usp.ime.line.ivprog.model.utils.StrUtilities;
import usp.ime.line.ivprog.model.utils.Tracking;

public class Ilm extends JApplet implements IlmProtocol {
    private static final long  serialVersionUID        = 1L;
    private static IlmProtocol _ilmProtocol;
    private static Ilm instance;
    private String             MA_PARAM_PropositionUrl = "";
    private String             MA_PARAM_Proposition    = "";
    public static String       ENCODE_TYPE             = "UTF-8";
    
    public static void main(String[] args) {
        final SystemControl ilmControl = new SystemControl();
        ilmControl.initialize(false, args, new IlmSystemFactory());
        _ilmProtocol = ilmControl.getProtocol();
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ilmControl.startDesktopGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
    
    public void init() {
        String[] args = GetParam();
        final SystemControl ilmControl = new SystemControl();
        ilmControl.initialize(true, args, new IlmSystemFactory());
        _ilmProtocol = ilmControl.getProtocol();
        IlmBaseGUI gui = (IlmBaseGUI) ilmControl.getAppletGUI();
        add(gui);
        String paramPropositionURL = "", // it is a string content of IVP or it is an URL of the content file
        paramPropositionIsURL = ""; // if "true" => 'paramPropositionSTR' it is an URL with file address
        paramPropositionURL = getParameter("MA_PARAM_Proposition");
        paramPropositionIsURL = getParameter("MA_PARAM_PropositionURL");
        Tracking.getInstance().setBase(getParameter("MA_PARAM_addresPOST"));
        String fileAsString = StrUtilities.readFromURL(this, paramPropositionURL);
        byte[] decoded = DatatypeConverter.parseBase64Binary(fileAsString);
        fileAsString = new String(decoded);
        if (fileAsString != null && !"".equals(fileAsString)) {
            (gui).openAssignmentFromURL(fileAsString);
        }
        instance = this;
    }
    
    private String[] GetParam() {
        String[] s = new String[1];
        s[0] = "";
        return s;
    }
    
    public float getEvaluation() {
        float resposta =_ilmProtocol.getEvaluation(); 
        return resposta;
    }
    
    public String getAnswer() {
        String str = _ilmProtocol.getAnswer();
        byte[] message = str.getBytes();
        String encoded = DatatypeConverter.printBase64Binary(message);
        return encoded;
    }
    
    public ZipFile getAssignmentPackage() {
        return _ilmProtocol.getAssignmentPackage();
    }
    
    public static Ilm getInstance(){
        return instance;
    }
}
