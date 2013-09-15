package ilm.framework;

import java.util.Vector;
import java.util.HashMap;
import javax.swing.JFrame;
import ilm.framework.assignment.AssignmentControl;
import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.comm.CommControl;
import ilm.framework.comm.ICommunication;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;
import ilm.framework.gui.AuthoringGUI;
import ilm.framework.gui.BaseGUI;
import ilm.framework.gui.IlmBaseGUI;
import ilm.framework.modules.IlmModule;

public abstract class SystemFactory {

    private DomainModel model;
    private DomainConverter converter;

    public final DomainModel getDomainModel(SystemConfig config) {
        if (model == null) {
            model = createDomainModel();
        }
        return model;
    }

    protected abstract DomainModel createDomainModel();

    public final DomainConverter getDomainConverter() {
        if (converter == null) {
            converter = createDomainConverter();
        }
        return converter;
    }

    protected abstract DomainConverter createDomainConverter();

    public abstract DomainGUI createDomainGUI(SystemConfig config, DomainModel domainModel);

    public BaseGUI createBaseGUI(SystemConfig config, IAssignment assignment, SystemFactory factory) {
        BaseGUI gui = new IlmBaseGUI();
        gui.setComponents(config, assignment, factory);
        return gui;
    }

    public abstract AuthoringGUI createAuthoringGUI(DomainGUI domainGUI, String proposition,
            AssignmentState initial, AssignmentState current, AssignmentState expected,
            HashMap config, HashMap metadata);

    public JFrame createConfigGUI(HashMap map, String string) {
        return null;
    }

    public JFrame createMetadataGUI(HashMap map, String string) {
        return null;
    }

    public final CommControl createCommControl(SystemConfig config) {
        return new CommControl(config);
    }

    public final AssignmentControl createAssignmentControl(SystemConfig config,
            ICommunication comm, DomainModel model, DomainConverter converter) {
        AssignmentControl assignControl = new AssignmentControl(config, comm, model, converter);
        for (int i = 0; i < getIlmModuleList().size(); i++) {
            assignControl.addModule((IlmModule) getIlmModuleList().get(i));
        }
        return assignControl;
    }

    protected Vector getIlmModuleList() {
        return new Vector();
    }
}
