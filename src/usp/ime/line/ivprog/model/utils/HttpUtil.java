package usp.ime.line.ivprog.model.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * @author Tulio Faria <tuliofaria@gmail.com>
 */
public class HttpUtil {
    
    public static String METHOD_POST           = "POST";
    public static String METHOD_POST_MULTIDATA = "PostMultidata";
    public static String METHOD_GET            = "GET";
    private Map          queryStringVars;
    private Map          postVars;
    private Map          uploadFiles;
    private String       boundary              = "";
    private String       outputContent         = "";
    private OutputStream output;
    
    public HttpUtil() {
        queryStringVars = new HashMap();
        postVars = new HashMap();
        uploadFiles = new HashMap();
    }
    
    public void addQueryStringVariable(String name, String value) {
        queryStringVars.put(name, value);
    }
    
    public void addPostVariable(String name, String value) {
        postVars.put(name, value);
    }
    
    public void addFile(String fieldname, String filename, Object file) {
        Map fileData = new HashMap();
        fileData.put("filename", filename);
        fileData.put("file", file);
        uploadFiles.put(fieldname, fileData);
    }
    
    private HttpURLConnection createConnection(String method, String destinationUrl) throws Exception {
        URL url = null;
        HttpURLConnection httpConn = null;
        int code = -1;
        try {
            String queryString = getQueryString();
            if (!queryString.equals("")) {
                destinationUrl = destinationUrl + "?" + queryString;
            }
            url = new URL(destinationUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            if (uploadFiles.size() > 0) {
                method = METHOD_POST_MULTIDATA;
            }
            if (method.equals(METHOD_POST_MULTIDATA)) {
                httpConn.setRequestMethod("POST");
            } else {
                httpConn.setRequestMethod(method);
            }
            httpConn.setUseCaches(false);
            if ((uploadFiles.size() > 0) && (postVars.size() > 0)) {
                httpConn.setDoInput(true);
            }
            httpConn.setDoOutput(true);
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            // use multipart if has to upload files
            if ((method.equals(METHOD_POST_MULTIDATA)) || (method.equals(METHOD_POST))) {
                boundary = Long.toHexString(System.currentTimeMillis());
                httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            }
            // TODO calcular
            // httpConn.addRequestProperty("Content-length", contentStrPck.size() + "");
            String CHARSET = "UTF8";
            httpConn.connect();
            PrintWriter writer = null;
            output = httpConn.getOutputStream(); // exception throws here
            writer = new PrintWriter(new OutputStreamWriter(output, CHARSET), true); // true = autoFlush, important!
            sendPostVariables(writer, CHARSET);
            uploadFiles(writer);
            InputStreamReader in = new InputStreamReader((InputStream) httpConn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            String text = "";
            do {
                line = buff.readLine();
                text += line + "\n";
            } while (line != null);
            outputContent = text;
            return httpConn;
        } catch (IOException io) {
            throw new Exception("Server not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String getQueryString() {
        String retorno = "";
        Set keySet = queryStringVars.keySet();
        for (Object k : keySet) {
            retorno += "&" + k + "=" + queryStringVars.get(k);
        }
        if (!retorno.equals("")) {
            retorno = retorno.substring(1);
        }
        return retorno;
    }
    
    public String getResponse() {
        return outputContent;
    }
    
    public void makePostRequest(String url) throws Exception {
        createConnection("POST", url);
    }
    
    public void makeGetRequest(String url) throws Exception {
        createConnection("GET", url);
    }
    
    private void sendPostVariables(PrintWriter writer, String charset) {
        Set keySet = postVars.keySet();
        for (Object k : keySet) {
            writer.println("--" + boundary);
            writer.println("Content-Disposition: form-data; name=\"" + k + "\"");
            writer.println("Content-Type: text/plain; charset=" + charset);
            writer.println();
            writer.println(postVars.get(k));
        }
    }
    
    private void uploadFiles(PrintWriter writer) {
        Set keySet = uploadFiles.keySet();
        for (Object k : keySet) {
            Map fileData = (HashMap) uploadFiles.get(k);
            writer.println("--" + boundary);
            writer.println("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + fileData.get("filename") + "\"");
            writer.println("Content-Transfer-Encoding: binary");
            writer.println();
            InputStream input = null;
            try {
                if (fileData.get("file") instanceof File) {
                    input = new FileInputStream((File) fileData.get("file"));
                } else if (fileData.get("file") instanceof byte[]) {
                    input = new ByteArrayInputStream((byte[]) fileData.get("file"));
                }
                byte[] buffer = new byte[1024];
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
                output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException logOrIgnore) {
                    }
                }
            }
            writer.println(); // Important! Indicates end of binary boundary.
            // End of multipart/form-data.
            writer.println("--" + boundary + "--");
        }
    }
}
