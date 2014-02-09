package ilm.framework.assignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipFile;
import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.comm.ICommunication;
import ilm.framework.config.SystemConfig;
import ilm.framework.domain.DomainConverter;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;
import ilm.framework.modules.assignment.HistoryModule;
import ilm.framework.modules.assignment.ObjectListModule;
import ilm.framework.modules.assignment.UndoRedoModule;
import ilm.framework.modules.operation.AutomaticCheckingModule;

public final class AssignmentControl implements IAssignment, IAssignmentOperator, IlmProtocol, Serializable {

    private SystemConfig    _config;
    private DomainModel     _model;
    private DomainConverter _converter;
    private ICommunication  _comm;
    private Vector          _assignmentList;
    private HashMap         _moduleList;

    public AssignmentControl(SystemConfig config, ICommunication comm, DomainModel model, DomainConverter converter) {
        _config = config;
        _comm = comm;
        _model = model;
        _converter = converter;
        initModuleList();
        initAssignments();
    }

    private void initModuleList() {
        _moduleList = new HashMap();
        AutomaticCheckingModule module = new AutomaticCheckingModule(this, this);
        module.setModel(_model);
        addModule(module);
        addModule(new UndoRedoModule());
        addModule(new HistoryModule());
        addModule(new ObjectListModule());
    }

    public void addModule(IlmModule module) {
        _moduleList.put(module.getName(), module);
    }

    private void initAssignments() {
        int numberOfPackages;
        try {
            numberOfPackages = Integer.parseInt(_config.getValue(IlmProtocol.NUMBER_OF_PACKAGES));
        } catch (NumberFormatException e) {
            numberOfPackages = 0;
            // TODO inform the user that the number of assignment was wrong
        }
        _assignmentList = new Vector();
        if (numberOfPackages > 0) {
            for (int i = 0; i < numberOfPackages; i++) {
                String packageFileName = _config.getValue(IlmProtocol.ASSIGNMENT_PACKAGE_PATH + "_" + i);
                getConfigFromMetadataFile(loadMetadataFile(packageFileName));
                _assignmentList.addAll(createAssignments(loadAssignmentFiles(packageFileName)));
            }
        } else {
            _assignmentList.add(createNewAssignment());
        }
    }

    private Vector createAssignments(Vector stringList) {
        Vector assignmentList = new Vector();
        AssignmentParser parser = new AssignmentParser();
        int n = getNumberOfAssignments();
        for (int i = 0; i < stringList.size(); i++) {
            Assignment a = parser.convertStringToAssignment(_converter, (String) stringList.get(i));
            assignmentList.add(a);
            if (a.getExpectedAnswer() == null || a.getExpectedAnswer().getList().size() < 1 || !a.getInitialState().equals(a.getCurrentState())) {
                parser.setAssignmentModulesData(_converter, (String) stringList.get(i), _moduleList, i + n);
            } else {
                addAssignmentToModules();
            }
            setModulesAssignment(a);
        }
        return assignmentList;
    }

    private Assignment createNewAssignment() {
        AssignmentState initialState = _model.getNewAssignmentState();
        Assignment a = new Assignment("", initialState, initialState, null);
        addAssignmentToModules();
        setModulesAssignment(a);
        return a;
    }

    private void addAssignmentToModules() {
        Iterator moduleIterator = _moduleList.keySet().iterator();
        while (moduleIterator.hasNext()) {
            String key = (String) moduleIterator.next();
            if (_moduleList.get(key) instanceof AssignmentModule) {
                ((AssignmentModule) _moduleList.get(key)).addAssignment();
            }
        }
    }

    private void setModulesAssignment(Assignment assignment) {
        Iterator moduleIterator = _moduleList.keySet().iterator();
        while (moduleIterator.hasNext()) {
            String key = (String) moduleIterator.next();
            if (_moduleList.get(key) instanceof AssignmentModule) {
                if (((AssignmentModule) _moduleList.get(key)).getObserverType() != AssignmentModule.ACTION_OBSERVER) {
                    assignment.getCurrentState().addObserver((AssignmentModule) _moduleList.get(key));
                }
                if (((AssignmentModule) _moduleList.get(key)).getObserverType() != AssignmentModule.OBJECT_OBSERVER) {
                    ((AssignmentModule) _moduleList.get(key)).setDomainModel(_model);
                    ((AssignmentModule) _moduleList.get(key)).setActionObservers(_moduleList.values());
                }
                ((AssignmentModule) _moduleList.get(key)).setState(assignment.getCurrentState());
            }
        }
    }

    private String loadMetadataFile(String packageFileName) {
        try {
            return _comm.readMetadataFile(packageFileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void getConfigFromMetadataFile(String metadataFileContent) {
        AssignmentParser parser = new AssignmentParser();
        HashMap config = parser.convertStringToMap(metadataFileContent, IlmProtocol.CONFIG_LIST_NODE);
        Iterator configIterator = config.keySet().iterator();
        while (configIterator.hasNext()) {
            String key = (String) configIterator.next();
            _config.setParameter(key, (String) config.get(key));
        }
    }

    private Vector loadAssignmentFiles(String packageFileName) {
        AssignmentParser parser = new AssignmentParser();
        String metadataFileContent = loadMetadataFile(packageFileName);
        Vector assignmentFileList = parser.getAssignmentFileList(metadataFileContent);
        HashMap metadata = parser.convertStringToMap(metadataFileContent, IlmProtocol.METADATA_LIST_NODE);
        try {
            Vector assignmentList = _comm.readAssignmentFiles(packageFileName, assignmentFileList);
            return parser.mergeMetadata(assignmentList, metadata);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see IAssignment
     * 
     * @return the index of newly created assignment requested by AuthoringGUI in BaseGUI
     */
    public ZipFile saveAssignmentPackage(Vector assignmentList, String fileName) {
        AssignmentParser parser = new AssignmentParser();
        String metadataFileContent = parser.createMetadataFileContent(assignmentList, _config.toString());
        Vector assignmentNameList = parser.getAssignmentFileList(metadataFileContent);
        Vector assignmentContentList = new Vector();
        String assignmentContent = "";
        for (int i = 0; i < assignmentList.size(); i++) {
            assignmentContent = parser.convertAssignmentToString(_converter, (Assignment) assignmentList.get(i));
            if (((Assignment) assignmentList.get(i)).getExpectedAnswer() == null || ((Assignment) assignmentList.get(i)).getExpectedAnswer().getList().size() < 1
                    || !((Assignment) assignmentList.get(i)).getInitialState().equals(((Assignment) assignmentList.get(i)).getCurrentState())) {
                assignmentContent = parser.getAssignmentModulesData(_converter, assignmentContent, _moduleList, i);
            }
            assignmentContentList.add(assignmentContent);
        }
        try {
            return _comm.writeAssignmentPackage(fileName, metadataFileContent, null, null, assignmentNameList, assignmentContentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int openAssignmentPackage(String fileName) {
        int initIndex = _assignmentList.size();
        _assignmentList.addAll(createAssignments(loadAssignmentFiles(fileName)));
        getConfigFromMetadataFile(loadMetadataFile(fileName));
        return initIndex;
    }

    public AssignmentState newAssignment() {
        Assignment a = createNewAssignment();
        _assignmentList.add(a);
        return a.getCurrentState();
    }

    public void closeAssignment(int index) {
        _assignmentList.remove(index);
        Iterator moduleIterator = _moduleList.keySet().iterator();
        while (moduleIterator.hasNext()) {
            String key = (String) moduleIterator.next();
            if (_moduleList.get(key) instanceof AssignmentModule) {
                ((AssignmentModule) _moduleList.get(key)).removeAssignment(index);
            }
        }
    }

    public int getNumberOfAssignments() {
        return _assignmentList.size();
    }

    public HashMap getIlmModuleList() {
        return _moduleList;
    }

    public AssignmentState getCurrentState(int index) {
        return ((Assignment) _assignmentList.get(index)).getCurrentState();
    }

    public AssignmentState getInitialState(int index) {
        return ((Assignment) _assignmentList.get(index)).getInitialState();
    }

    public AssignmentState getExpectedAnswer(int index) {
        return ((Assignment) _assignmentList.get(index)).getExpectedAnswer();
    }

    public HashMap getConfig(int index) {
        return ((Assignment) _assignmentList.get(index)).getConfig();
    }

    public HashMap getMetadata(int index) {
        return ((Assignment) _assignmentList.get(index)).getMetadata();
    }

    public String getProposition(int index) {
        return ((Assignment) _assignmentList.get(index)).getProposition();
    }

    /**
     * @see IAssignmentOperator
     * 
     * @return the converter of file content to domain objects and actions requested by the iLM Modules
     */
    public DomainConverter getConverter() {
        return _converter;
    }

    /**
     * @see IAssignmentOperator
     * 
     * @return the reader and writer of files requested by the iLM Modules
     */
    public ICommunication getFileRW() {
        return _comm;
    }

    public void print() {
        for (int i = 0; i < _assignmentList.size(); i++) {
            Assignment a = (Assignment) _assignmentList.get(i);
            a.print();
        }
        Iterator moduleIterator = _moduleList.keySet().iterator();
        while (moduleIterator.hasNext()) {
            String key = (String) moduleIterator.next();
            ((IlmModule) _moduleList.get(key)).print();
        }
    }

    public float getEvaluation() {
        return ((AutomaticCheckingModule) _moduleList.get(IlmProtocol.AUTO_CHECKING_MODULE_NAME)).getEvaluation();
    }

    public String getAnswer() {
        return ((AutomaticCheckingModule) _moduleList.get(IlmProtocol.AUTO_CHECKING_MODULE_NAME)).getAnswer();
    }

    public ZipFile getAssignmentPackage() {
        // TODO A better maybe random name generator
        String fileName = "skdjhf";
        return saveAssignmentPackage(_assignmentList, fileName);
    }

}
