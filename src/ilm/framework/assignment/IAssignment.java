package ilm.framework.assignment;

import ilm.framework.assignment.model.AssignmentState;
import java.util.Vector;
import java.util.HashMap;
import java.util.zip.ZipFile;

public interface IAssignment {
    public String getProposition(int index);
    
    public AssignmentState getCurrentState(int index);
    
    public AssignmentState getInitialState(int index);
    
    public AssignmentState getExpectedAnswer(int index);
    
    public HashMap getConfig(int index);
    
    public HashMap getMetadata(int index);
    
    public HashMap getIlmModuleList();
    
    public int getNumberOfAssignments();
    
    public int openAssignmentPackage(String fileName);
    
    public ZipFile saveAssignmentPackage(Vector assignmentList, String fileName);
    
    public AssignmentState newAssignment();
    
    public void closeAssignment(int index);

}
