package ilm.modules.operation.scorm;

import ilm.framework.assignment.IAssignment;
import ilm.framework.assignment.IAssignmentOperator;
import ilm.framework.modules.OperationModule;

public class ScormModule extends OperationModule {
    public ScormModule(IAssignment assignments, IAssignmentOperator operator) {
        setAssignmentList(assignments);
        setAssignmentOperator(operator);
    }
    
    public void print() {
    }
}
