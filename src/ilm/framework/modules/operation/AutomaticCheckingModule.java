package ilm.framework.modules.operation;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.Assignment;
import ilm.framework.assignment.AssignmentControl;
import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;
import ilm.framework.domain.DomainModel;
import ilm.framework.modules.OperationModule;

import java.io.Serializable;
import java.util.Vector;

import usp.ime.line.ivprog.model.IVPProgram;

public class AutomaticCheckingModule extends OperationModule implements Serializable {
    
    private DomainModel _model;
    
    public AutomaticCheckingModule(IAssignment assignmentControl, IAssignmentOperator operator) {
        setAssignmentList(assignmentControl);
        setAssignmentOperator(operator);
        _name = IlmProtocol.AUTO_CHECKING_MODULE_NAME;
        _gui = new AutoCheckingModuleToolbar(this);
    }
    
    public float getEvaluation() {
        /*
        if (_assignmentList.getExpectedAnswer(_assignmentIndex) == null) {
            return 0;
        }
        */
        Assignment a = (Assignment) ((AssignmentControl)_assignmentList).get_assignmentList().get(_assignmentIndex);
        float resposta = 0;
        System.out.println("CHEGOU AQUI NA CHAMADA!");
        resposta = (((IVPProgram)_model).getEvaluation(a.getTestCase()));
        return resposta;
    }
    
    public String getAnswer() {
        Vector list = _assignmentList.getCurrentState(_assignmentIndex).getList();
        return _operator.getConverter().convertObjectToString(list);
    }
    
    public void setModel(DomainModel model) {
        _model = model;
    }
    
    public void print() {
    }
    
    public boolean hasExpectedAnswer() {
        if (_assignmentList.getExpectedAnswer(_assignmentIndex) == null) {
            return false;
        }
        if (_assignmentList.getExpectedAnswer(_assignmentIndex).getList().size() < 1) {
            return false;
        }
        return true;
    }
}
