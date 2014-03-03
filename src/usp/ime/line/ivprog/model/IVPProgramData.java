package usp.ime.line.ivprog.model;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import usp.ime.line.ivprog.model.components.datafactory.DataFactory;

public class IVPProgramData {
    
    private HashMap     globalVariables;
    private HashMap     preDefinedFunctions;
    private HashMap     functionMap;
    private List        variableListeners;
    private List        functionListeners;
    private List        expressionListeners;
    private List        operationListeners;
    private HashMap     codeListeners;
    private HashMap     modelMapping;
    private HashMap     viewMapping;
    
    private int index = 0;
    
    private Vector dataList;
    
    private static IVPProgramData instance;
    
    public IVPProgramData() {
        dataList = new Vector();
    }
    
    public void addListInstance() {
        HashMap map = new HashMap();
        map.put("globalvariables", new HashMap());
        map.put("predefinedfunctions", new HashMap());
        map.put("functionmap", new HashMap());
        map.put("codelisteners", new HashMap());
        map.put("modelmapping", new HashMap());
        map.put("viewmapping", new HashMap());
        map.put("variablelisteners", new Vector());
        map.put("functionlisteners", new Vector());
        map.put("expressionlisteners", new Vector());
        map.put("operationlisteners", new Vector());
        dataList.add(map);
    }

    public HashMap getGlobalVariables() {
        return (HashMap) ((HashMap) dataList.get(index)).get("globalvariables");
    }
    
    public HashMap getPreDefinedFunctions() {
        return (HashMap) ((HashMap) dataList.get(index)).get("predefinedfunctions");
    }
    
    public HashMap getFunctionMap() {
        return (HashMap) ((HashMap) dataList.get(index)).get("functionmap");
    }
    
    public List getVariableListeners() {
        return (Vector) ((HashMap) dataList.get(index)).get("variablelisteners");
    }
    
    public List getFunctionListeners() {
        return (Vector) ((HashMap) dataList.get(index)).get("functionlisteners");
    }
    
    public List getExpressionListeners() {
        return (Vector) ((HashMap) dataList.get(index)).get("expressionlisteners");
    }
    
    public List getOperationListeners() {
        return (Vector) ((HashMap) dataList.get(index)).get("operationlisteners");
    }
    
    public HashMap getCodeListeners() {
        return (HashMap) ((HashMap) dataList.get(index)).get("codelisteners");
    }
    
    public HashMap getModelMapping() {
        return (HashMap) ((HashMap) dataList.get(index)).get("modelmapping");
    }
    
    public HashMap getViewMapping() {
        return (HashMap) ((HashMap) dataList.get(index)).get("viewmapping");
    }
    
    public static IVPProgramData getData(){
        if(instance == null){
            instance = new IVPProgramData();
        }
        return instance;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}