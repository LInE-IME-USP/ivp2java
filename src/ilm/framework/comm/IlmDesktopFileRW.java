package ilm.framework.comm;

import ilm.framework.IlmProtocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.DatatypeConverter;

import usp.ime.line.ivprog.Ilm;
import usp.ime.line.ivprog.model.utils.StrUtilities;

public class IlmDesktopFileRW implements ICommunication {
    public IlmDesktopFileRW() {
    }
    
    public String readMetadataFile(String packageName) throws IOException {
        try {
            InputStream f = new FileInputStream(packageName);
            String fileString = convertInputStreamToString(f);
            byte[] decoded = DatatypeConverter.parseBase64Binary(fileString);
            fileString = new String(decoded);
            String metadata = fileString.substring(0, fileString.lastIndexOf("</package>"));
            return metadata;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Vector readResourceFiles(String packageName, Vector resourceList) {
        return null;
    }
    
    public Vector readAssignmentFiles(String packageName, Vector assignmentFileList) throws IOException {
        Vector assignmentContentList = new Vector();
        try {
            // ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
            String fullName = (packageName.indexOf(".ivp2") != -1) ? packageName : packageName + ".ivp2";
            InputStream f = new FileInputStream(fullName);
            String fileString = convertInputStreamToString(f);
            byte[] decoded = DatatypeConverter.parseBase64Binary(fileString);
            fileString = new String(decoded);
            assignmentContentList.add(fileString.substring(fileString.lastIndexOf("</package>"), fileString.length()));
            return assignmentContentList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public ZipFile writeAssignmentPackage(String packageName, String metadata, Vector resourceNameList, Vector resourceList, Vector assignmentNameList, Vector assignmentList) {
        String str = metadata;
        for (int i = 0; i < assignmentNameList.size(); i++) {
            str += assignmentList.get(i);
        }
        String fullName = (packageName.indexOf(".ivp2") != -1) ? packageName : packageName + ".ivp2";
        byte[] message = str.getBytes();
        String encoded = DatatypeConverter.printBase64Binary(message);
        writeFile(encoded, fullName);
        return null;
    }
    
    private void writeFile(String content, String fileName) {
        try {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String convertInputStreamToString(InputStream in) {
        if (in != null) {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
