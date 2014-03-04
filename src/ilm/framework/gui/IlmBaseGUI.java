package ilm.framework.gui;

import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainGUI;
import ilm.framework.gui.BaseGUI;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;
import ilm.framework.modules.assignment.HistoryModule;
import ilm.framework.modules.assignment.UndoRedoModule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.FlowLayout;
import java.awt.Color;

public class IlmBaseGUI extends BaseGUI {
    private static final long serialVersionUID = 1L;
    private JPanel            buttonsMenu;
    private JPanel            panel;
    private JTabbedPane       tabbedPane;
    private JButton           authoringBtn;
    private JButton           newAssBtn;
    private JButton           closeAssBtn;
    private JButton           openAssBtn;
    private JButton           saveAssBtn;
    private int               tabCount;
    private boolean isOpening = false;
    
    public IlmBaseGUI() {
        setLayout(new BorderLayout(0, 0));
        buttonsMenu = new JPanel();
        buttonsMenu.setBackground(FlatUIColors.MAIN_BG);
        FlowLayout flowLayout = (FlowLayout) buttonsMenu.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        add(buttonsMenu, BorderLayout.NORTH);
        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                setActiveAssignment();
            }
        });
        tabCount = 0;
    }
    
    protected void initAssignments() {
        if (_assignments.getNumberOfAssignments() == 1) {
            tabbedPane.setVisible(false);
            _domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
            int index = _domainGUIList.size() - 1;
            ((DomainGUI) _domainGUIList.get(index)).setAssignment(_assignments.getProposition(0), _assignments.getCurrentState(0), _assignments.getIlmModuleList().values());
            panel.add((Component) _domainGUIList.get(index));
            _authoringGUIList.add(_factory.createAuthoringGUI((DomainGUI) _domainGUIList.get(index), _assignments.getProposition(0), _assignments.getInitialState(0), _assignments.getCurrentState(0),
                    _assignments.getExpectedAnswer(0), _assignments.getConfig(0), _assignments.getMetadata(0)));
            setActiveAssignment();
            initModelAndUI(index);
        } else {
            panel.add(tabbedPane);
            for (int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
                tabbedPane.setVisible(true);
                initAssignment(_assignments.getCurrentState(i));
            }
        }
    }

    private void initModelAndUI(int index) {
        Services.getService().getController().getProgram().addFunctionListener( (IFunctionListener) _domainGUIList.get(index));
        Services.getService().getController().initializeModel();
        gambiarraDoRo(_assignments.getIlmModuleList().values());
    }
    
    private void initAssignment(AssignmentState curState) {
        _domainGUIList.add(_factory.createDomainGUI(_config, _factory.getDomainModel(_config)));
        int index = _domainGUIList.size() - 1;
        ((DomainGUI) _domainGUIList.get(index)).setAssignment(_assignments.getProposition(index), curState, _assignments.getIlmModuleList().values());
        tabbedPane.addTab("assign" + (tabCount++), (Component) _domainGUIList.get(index));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        setActiveAssignment();
        initModelAndUI(index);
        _authoringGUIList.add(_factory.createAuthoringGUI((DomainGUI) _domainGUIList.get(index), _assignments.getProposition(index), _assignments.getInitialState(index),
                _assignments.getCurrentState(index), _assignments.getExpectedAnswer(index), _assignments.getConfig(index), _assignments.getMetadata(index)));
    }
    
    public void initToolbar(Collection moduleList) {
        addToolBarButtons();
        Iterator moduleIterator = moduleList.iterator();
        while (moduleIterator.hasNext()) {
            IlmModule module = (IlmModule) moduleIterator.next();
            buttonsMenu.add(module.getGUI());
            buttonsMenu.add(Box.createHorizontalStrut(50));
        }
    }
    
    private void addToolBarButtons() {
        setAuthoringButton();
        setNewAssignmentButton();
        setCloseAssignmentButton();
        setOpenAssignmentButton();
        setSaveAssignmentButton();
    }
    
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        // update comes from _config
        // check for each property if changed
        // check for language
        // apply changes
    }
    
    protected void setActiveAssignment() {
        int index = tabbedPane.getSelectedIndex();
        if (index == -1) {
            updateAssignmentIndex(0);
            Services.getService().getController().setGui((IVPDomainGUI) _domainGUIList.get(0));
        } else {
            updateAssignmentIndex(index);
            Services.getService().getController().setGui((IVPDomainGUI) _domainGUIList.get(index));
        }
    }
    
    protected void setAuthoringButton() {
        authoringBtn = makeButton("authoring", "ASSIGNMENT AUTHORING", ResourceBundleIVP.getString("authoringBtn.Tip"), ResourceBundleIVP.getString("authoringBtn.AltText"));
        buttonsMenu.add(authoringBtn);
        authoringBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startAuthoring();
            }
        });
    }
    
    protected void startAuthoring() {
        ((JFrame) _authoringGUIList.get(_activeAssignment)).setVisible(true);
    }
    
    protected void setNewAssignmentButton() {
        newAssBtn = makeButton("newassignment", "NEW ASSIGNMENT", ResourceBundleIVP.getString("newAssBtn.Tip"), ResourceBundleIVP.getString("newAssBtn.AltText"));
        buttonsMenu.add(newAssBtn);
        newAssBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewAssignment();
            }
        });
    }
    
    protected void addNewAssignment() {
        if (_assignments.getNumberOfAssignments() == 1) {
            panel.removeAll();
            panel.add(tabbedPane);
            tabbedPane.setVisible(true);
            tabbedPane.addTab("assign" + (tabCount++), (Component) _domainGUIList.get(0));
            AssignmentState state = _assignments.newAssignment();
            initAssignment(state);
        } else {
            initAssignment(_assignments.newAssignment());
        }
        updateCloseButton();
    }
    
    protected void setCloseAssignmentButton() {
        closeAssBtn = makeButton("closeassignment", "CLOSE ASSIGNMENT", ResourceBundleIVP.getString("closeAssBtn.Tip"), ResourceBundleIVP.getString("closeAssBtn.Tip"));
        buttonsMenu.add(closeAssBtn);
        closeAssBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeAssignment(tabbedPane.getSelectedIndex());
            }
        });
        updateCloseButton();
    }
    
    protected void closeAssignment(int index) {
        if (_assignments.getNumberOfAssignments() == 1) {
        } else if (_assignments.getNumberOfAssignments() == 2) {
            closeActiveAssignment();
            panel.removeAll();
            panel.add((Component) _domainGUIList.get(0));
            ((JComponent) _domainGUIList.get(0)).setVisible(true);
            updateCloseButton();
        } else {
            closeActiveAssignment();
        }
    }
    
    private void updateCloseButton() {
        if (_assignments.getNumberOfAssignments() == 1) {
            closeAssBtn.setEnabled(false);
        } else {
            closeAssBtn.setEnabled(true);
        }
    }
    
    private void closeActiveAssignment() {
        int index = _activeAssignment;
        _assignments.closeAssignment(index);
        tabbedPane.remove(index);
        _domainGUIList.remove(index);
        _authoringGUIList.remove(index);
        setActiveAssignment();
    }
    
    protected void setOpenAssignmentButton() {
        openAssBtn = makeButton("openassignment", "OPEN ASSIGNMENT FILE", ResourceBundleIVP.getString("openAssBtn.Tip"), ResourceBundleIVP.getString("openAssBtn.AltText"));
        buttonsMenu.add(openAssBtn);
        openAssBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAssignmentFile(getFileNameFromWindow("Choose file"));
            }
        });
    }
    
    protected void openAssignmentFile(String fileName) {
        if (fileName == null) {
            return;
        }
        int initialIndex = _assignments.openAssignmentPackage(fileName);
        for (int i = initialIndex; i < _assignments.getNumberOfAssignments(); i++) {
            if (_domainGUIList.size() == 1) {
                panel.removeAll();
                panel.add(tabbedPane);
                tabbedPane.setVisible(true);
                tabbedPane.addTab("assign" + (tabCount++), (Component) _domainGUIList.get(0));
            }
            isOpening = true;
            initAssignment(_assignments.getCurrentState(i));
            isOpening = false;
        }
        updateCloseButton();
    }
    
    private String getFileNameFromWindow(String option) {
        JFileChooser fc = new JFileChooser();
        int returnval = fc.showDialog(this, option);
        if (returnval == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        } else if (returnval == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(this, "Error while choosing file.", "Error file", JOptionPane.OK_OPTION);
        }
        return null;
    }
    
    protected void setSaveAssignmentButton() {
        saveAssBtn = makeButton("save", "SAVE ASSIGNMENT FILE", ResourceBundleIVP.getString("saveAssBtn.Tip"), ResourceBundleIVP.getString("saveAssBtn.AltText"));
        buttonsMenu.add(saveAssBtn);
        saveAssBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAssignmentFile(getFileNameFromWindow("Choose filename"));
            }
        });
    }
    
    protected void saveAssignmentFile(String fileName) {
        if (fileName == null) {
            return;
        }
        Vector list = new Vector();
        for (int i = 0; i < _assignments.getNumberOfAssignments(); i++) {
            if (((AuthoringGUI) _authoringGUIList.get(i)).getProposition().length() > 1) {
                list.add(((AuthoringGUI) _authoringGUIList.get(i)).getAssignment());
            } else {
                Assignment a = new Assignment(_assignments.getProposition(i), _assignments.getInitialState(i), _assignments.getCurrentState(i), _assignments.getExpectedAnswer(i));
                if (tabbedPane.getTabCount() == 0) {
                    a.setName(IlmProtocol.ASSIGNMENT_FILE_NODE + tabCount);
                } else {
                    a.setName(tabbedPane.getTitleAt(i));
                }
                a.setConfig(_assignments.getConfig(i));
                a.setMetadata(_assignments.getMetadata(i));
                list.add(a);
            }
        }
        _assignments.saveAssignmentPackage(list, fileName);
    }
    
    /**
     * Gambiarra pra restaurar o estado da atividade.
     * @param moduleList
     */
    public void gambiarraDoRo(Collection moduleList){
        if(!isOpening) return;
        System.out.println("entrou na gambiarra...");
        Iterator moduleIterator = moduleList.iterator();
        HistoryModule h = null;
        UndoRedoModule u = null;
        while (moduleIterator.hasNext()) {
            IlmModule module = (IlmModule) moduleIterator.next();
            if (module instanceof AssignmentModule) {
                if (module.getName().equals(IlmProtocol.HISTORY_MODULE_NAME)){
                    h = (HistoryModule) module;
                }else if(module.getName().equals(IlmProtocol.UNDO_REDO_MODULE_NAME)){
                    u = (UndoRedoModule) module;
                }
            }
        }
        if(h != null)
            h.executeActions();
        if(u != null)
            u.restoreFromFile();
    }
}
