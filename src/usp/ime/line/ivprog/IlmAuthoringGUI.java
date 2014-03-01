package usp.ime.line.ivprog;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.gui.AuthoringGUI;

import java.awt.BorderLayout;
import java.util.Vector;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IlmAuthoringGUI extends AuthoringGUI {
    public void update(Observable arg0, Object arg1) {
    }
    
    protected void initFields() {
    }
    
    protected String getProposition() {
        return "";
    }
    
    protected String getAssignmentName() {
        return "";
    }
    
    protected AssignmentState getInitialState() {
        return null;
    }
    
    protected AssignmentState getExpectedAnswer() {
        return null;
    }
    
    protected HashMap getConfig() {
        return null;
    }
    
    protected HashMap getMetadata() {
        return null;
    }
}
