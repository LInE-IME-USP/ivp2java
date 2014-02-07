package ilm.framework.comm;

import ilm.framework.IlmProtocol;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class IlmDesktopFileRW implements ICommunication {

    public String readMetadataFile(String packageName) throws IOException {
        File sourceZipFile = new File(packageName);
        try {
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
            InputStream inMetadata = zipFile.getInputStream(zipFile.getEntry(IlmProtocol.METADATA_FILENAME));
            String metadataContent = convertInputStreamToString(inMetadata);
            zipFile.close();
            return metadataContent;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Vector readResourceFiles(String packageName, Vector resourceList) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vector readAssignmentFiles(String packageName, Vector assignmentFileList) throws IOException {
        File sourceZipFile = new File(packageName);
        try {
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
            InputStream in;
            Vector assignmentContentList = new Vector();
            for (int i = 0; i < assignmentFileList.size(); i++) {
                String fileName = (String) assignmentFileList.get(i);
                in = zipFile.getInputStream(zipFile.getEntry(fileName));
                assignmentContentList.add(convertInputStreamToString(in));
            }
            zipFile.close();
            return assignmentContentList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ZipFile writeAssignmentPackage(String packageName, String metadata, Vector resourceNameList, Vector resourceList, Vector assignmentNameList, Vector assignmentList) {
        writeFile(metadata, IlmProtocol.METADATA_FILENAME);
        for (int i = 0; i < assignmentNameList.size(); i++) {
            writeFile((String) assignmentList.get(i), (String) assignmentNameList.get(i));
        }
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(packageName));
            BufferedReader in = new BufferedReader(new FileReader(IlmProtocol.METADATA_FILENAME));
            zos.putNextEntry(new ZipEntry(IlmProtocol.METADATA_FILENAME));
            int c;
            while ((c = in.read()) != -1)
                zos.write(c);
            in.close();
            zos.closeEntry();
            for (int i = 0; i < assignmentNameList.size(); i++) {
                String fileName = (String) assignmentNameList.get(i);
                in = new BufferedReader(new FileReader(fileName));
                zos.putNextEntry(new ZipEntry(fileName));
                while ((c = in.read()) != -1)
                    zos.write(c);
                in.close();
                zos.closeEntry();
            }
            zos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Return something meaningfull
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
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
