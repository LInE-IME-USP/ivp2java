package ilm.framework.comm;

import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipFile;

public class IlmAppletFileRW implements ICommunication {
	public String readMetadataFile(String file) {
		return null;
	}

	public Vector readResourceFiles(String packageName, Vector resourceList) {
		return null;
	}

	public Vector readAssignmentFiles(String file, Vector assignmentList) {
		return null;
	}

	public ZipFile writeAssignmentPackage(String packageName, String metadata, Vector resourceNameList, Vector resourceList,
	        Vector assignmentNameList, Vector assignmentList) throws IOException {
		return null;
	}
}
