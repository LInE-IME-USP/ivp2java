package ilm.framework.comm;

import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipFile;

public interface ICommunication {
    public String readMetadataFile(String packageName) throws IOException;
    
    public Vector readResourceFiles(String packageName, Vector resourceList) throws IOException;
    
    public Vector readAssignmentFiles(String packageName, Vector assignmentList) throws IOException;
    
    public ZipFile writeAssignmentPackage(String packageName, String metadata, Vector resourceNameList, Vector resourceList, Vector assignmentNameList, Vector assignmentList) throws IOException;
}
