package usp.ime.line.ivprog;

import java.util.Vector;
import java.util.HashMap;

import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import ilm.framework.SystemFactory;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.gui.AuthoringGUI;
import ilm.framework.modules.IlmModule;

public class IlmSystemFactory extends SystemFactory {
    public DomainModel createDomainModel() {
        IVPProgram program = new IVPProgram();
        Services.getService().getController().setProgram(program);
        return program;
    }
    
    public DomainConverter createDomainConverter() {
        return new IVPDomainConverter();
    }
    
    public DomainGUI createDomainGUI(SystemConfig config, DomainModel model) {
        IVPDomainGUI domainGUI = new IVPDomainGUI();
        domainGUI.initDomainActionList(model);
        Services.getService().getController().setGui(domainGUI);
        Services.getService().getController().initDomainActionList(model);
        return domainGUI;
    }
    
    public AuthoringGUI createAuthoringGUI(DomainGUI domainGUI, String proposition, AssignmentState initial, AssignmentState current, AssignmentState expected, HashMap config, HashMap metadata) {
        AuthoringGUI gui = new IlmAuthoringGUI();
        gui.setComponents(config, domainGUI, metadata);
        gui.setAssignment(proposition, initial, current, expected);
        return gui;
    }
    
    protected Vector getIlmModuleList() {
        Vector list = new Vector();
        // list.add(new ScriptModule());
        // list.add(new ExampleTracingTutorModule());
        // list.add(new ScormModule());
        return list;
    }
}