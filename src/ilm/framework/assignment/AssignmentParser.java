package ilm.framework.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;

final class AssignmentParser {
    /**
     * CONVERSÃO DE STRING PARA ATIVIDADE (LEITURA DE ARQUIVOS)
     * 
     * @param converter
     * @param assignmentString
     * @return
     */
    public Assignment convertStringToAssignment(DomainConverter converter, String assignmentString) {
        String proposition = getProposition(assignmentString);
        AssignmentState initialState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_INITIAL_NODE);
        AssignmentState currentState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_CURRENT_NODE);
        AssignmentState expectedState = getState(converter, assignmentString, IlmProtocol.ASSIGNMENT_EXPECTED_NODE);
        HashMap config = convertStringToMap(assignmentString, IlmProtocol.CONFIG_LIST_NODE);
        HashMap metadata = convertStringToMap(assignmentString, IlmProtocol.METADATA_LIST_NODE);
        Assignment assignment = new Assignment(proposition, initialState, currentState, expectedState);
        assignment.setConfig(config);
        assignment.setMetadata(metadata);
        return assignment;
    }
    
    public String getProposition(String assignmentString) {
        int startIndex = assignmentString.indexOf("<" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
        int endIndex = assignmentString.indexOf("</" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">");
        return assignmentString.substring(startIndex + 2 + IlmProtocol.ASSIGNMENT_PROPOSITION.length(), endIndex);
    }
    
    public AssignmentState getState(DomainConverter converter, String assignmentString, String nodeName) {
        AssignmentState state = new AssignmentState();
        int startIndex = assignmentString.indexOf("<" + nodeName + ">") + 2 + nodeName.length();
        int endIndex = assignmentString.indexOf("</" + nodeName + ">");
        if (startIndex == -1 | endIndex == -1) {
            return state;
        }
        String objectListString = assignmentString.substring(startIndex, endIndex);
        Vector list = converter.convertStringToObject(objectListString);
        state.setList(list);
        return state;
    }
    
    public HashMap convertStringToMap(String xmlContent, String nodeName) {
        int listNodeLength = nodeName.length();
        int startIndex = xmlContent.indexOf("<" + nodeName + ">");
        int endIndex = xmlContent.indexOf("</" + nodeName + ">");
        String listString = xmlContent.substring(startIndex, endIndex + 3 + listNodeLength);
        HashMap map = new HashMap();
        int nodeLength = 0;
        endIndex = listNodeLength;
        do {
            startIndex = listString.indexOf("<", endIndex + 1);
            nodeLength = listString.indexOf(">", startIndex) - startIndex;
            endIndex = listString.indexOf("</", startIndex);
            if (startIndex == endIndex) {
                break;
            }
            map.put(listString.substring(startIndex + 1, startIndex + nodeLength), listString.substring(startIndex + nodeLength + 1, endIndex));
            // TODO define a better threshold - differences when there are
            // breaklines chars
        } while (endIndex < listString.lastIndexOf("</" + nodeName + ">") - nodeLength - 4);
        return map;
    }
    
    /**
     * CONVERSï¿½O DE ATIVIDADE PARA STRING (GRAVAï¿½ï¿½O DE ARQUIVOS)
     * 
     * @param converter
     * @param assignment
     * @return
     */
    public String convertAssignmentToString(DomainConverter converter, Assignment assignment) {
        String string = "<" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">";
        string += "<" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">" + assignment.getProposition() + "</" + IlmProtocol.ASSIGNMENT_PROPOSITION + ">";
        string += "<" + IlmProtocol.ASSIGNMENT_INITIAL_NODE + ">" + converter.convertObjectToString(assignment.getInitialState().getList()) + "</" + IlmProtocol.ASSIGNMENT_INITIAL_NODE + ">";
        if (assignment.getCurrentState().getList().size() > 0) {
            string += "<" + IlmProtocol.ASSIGNMENT_CURRENT_NODE + ">" + converter.convertObjectToString(assignment.getCurrentState().getList()) + "</" + IlmProtocol.ASSIGNMENT_CURRENT_NODE + ">";
        } else {
            string += "<" + IlmProtocol.ASSIGNMENT_CURRENT_NODE + "/>";
        }
        if (assignment.getExpectedAnswer() != null && assignment.getExpectedAnswer().getList().size() > 0) {
            string += "<" + IlmProtocol.ASSIGNMENT_EXPECTED_NODE + ">" + converter.convertObjectToString(assignment.getExpectedAnswer().getList()) + "</" + IlmProtocol.ASSIGNMENT_EXPECTED_NODE + ">";
        } else {
            string += "<" + IlmProtocol.ASSIGNMENT_EXPECTED_NODE + "/>";
        }
        string += "<" + IlmProtocol.CONFIG_LIST_NODE + ">" + convertMapToString(assignment.getConfig()) + "</" + IlmProtocol.CONFIG_LIST_NODE + ">";
        string += "<" + IlmProtocol.METADATA_LIST_NODE + ">" + convertMapToString(assignment.getMetadata()) + "</" + IlmProtocol.METADATA_LIST_NODE + ">";
        string += "</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">";
        return string;
    }
    
    /**
     * Convert a HashMap<String, String> to a XMLString with its contents. The order of the parameters is inverse alphabetical order of the keys.
     * 
     * @param map
     *            - the HashMap<String, String> to be converted
     * @return - the String containing the map content in XML form
     */
    public String convertMapToString(HashMap map) {
        String string = "";
        Iterator mapIterator = map.keySet().iterator();
        while (mapIterator.hasNext()) {
            String key = (String) mapIterator.next();
            string += "<" + key + ">" + map.get(key) + "</" + key + ">";
        }
        return string;
    }
    
    /**
     * COLETA/ESCRITA DE DADOS DO ARQUIVO DE METADADOS
     * 
     * @param metadataFileContent
     * @return
     */
    public Vector getAssignmentFileList(String metadataFileContent) {
        int listLength = IlmProtocol.FILE_LIST_NODE.length();
        int startIndex = metadataFileContent.indexOf("<" + IlmProtocol.FILE_LIST_NODE + ">");
        int endIndex = metadataFileContent.indexOf("</" + IlmProtocol.FILE_LIST_NODE + ">");
        String fileListString = metadataFileContent.substring(startIndex, endIndex + 3 + listLength);
        Vector assignmentFileList = new Vector();
        int fileLength = IlmProtocol.ASSIGNMENT_FILE_NODE.length();
        endIndex = 0;
        do {
            startIndex = fileListString.indexOf("<" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">", endIndex) + 2 + fileLength;
            endIndex = fileListString.indexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">", startIndex);
            assignmentFileList.add(fileListString.substring(startIndex, endIndex));
        } while (endIndex < fileListString.lastIndexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">"));
        return assignmentFileList;
    }
    
    public Vector mergeMetadata(Vector assignmentList, HashMap metadata) {
        String metadataString = "";
        Iterator metaDataIterator = metadata.keySet().iterator();
        while (metaDataIterator.hasNext()) {
            String key = (String) metaDataIterator.next();
            metadataString += "<" + key + ">" + metadata.get(key) + "</" + key + ">";
        }
        int metadataIndex = 0;
        String mergedAssignment = "";
        Vector mergedList = new Vector();
        for (int i = 0; i < assignmentList.size(); i++) {
            String assignment = (String) assignmentList.get(i);
            metadataIndex = assignment.indexOf("<" + IlmProtocol.METADATA_LIST_NODE + ">");
            if (metadataIndex == -1) {
                metadataString = "<" + IlmProtocol.METADATA_LIST_NODE + ">" + metadataString + "</" + IlmProtocol.METADATA_LIST_NODE + ">";
                mergedAssignment = assignment.substring(0, assignment.indexOf("</" + IlmProtocol.PACKAGE_NODE + ">"));
                mergedAssignment += metadataString + "</" + IlmProtocol.PACKAGE_NODE + ">";
                mergedList.add(mergedAssignment);
            } else {
                int endIndex = assignment.indexOf("</" + IlmProtocol.METADATA_LIST_NODE + ">");
                mergedAssignment = assignment.substring(0, endIndex);
                mergedAssignment += metadataString + "</" + IlmProtocol.METADATA_LIST_NODE + ">" + assignment.substring(endIndex + 3 + IlmProtocol.METADATA_LIST_NODE.length());
                mergedList.add(mergedAssignment);
            }
        }
        return mergedList;
    }
    
    public String createMetadataFileContent(Vector list, String config) {
        String string = "<" + IlmProtocol.PACKAGE_NODE + ">";
        if (list.size() < 1) {
            return null;
        }
        string += "<" + IlmProtocol.FILE_LIST_NODE + ">";
        for (int i = 0; i < list.size(); i++) {
            Assignment a = (Assignment) list.get(i);
            string += "<" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">" + a.getName() + "</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">";
        }
        string += "</" + IlmProtocol.FILE_LIST_NODE + "><" + IlmProtocol.CONFIG_LIST_NODE + ">" + config + "</" + IlmProtocol.CONFIG_LIST_NODE + "><" + IlmProtocol.METADATA_LIST_NODE + ">";
        HashMap mergedMetadata = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            Assignment a = (Assignment) list.get(i);
            mergedMetadata = mergeMap(mergedMetadata, a.getMetadata());
        }
        string += convertMapToString(mergedMetadata) + "</" + IlmProtocol.METADATA_LIST_NODE + "></" + IlmProtocol.PACKAGE_NODE + ">";
        return string;
    }
    
    private HashMap mergeMap(HashMap mergedMap, HashMap map) {
        Iterator mapIterator = map.keySet().iterator();
        while (mapIterator.hasNext()) {
            String key = (String) mapIterator.next();
            if (!mergedMap.containsKey(key)) {
                mergedMap.put(key, map.get(key));
            }
        }
        return mergedMap;
    }
    
    /**
     * CONVERSï¿½O DE DADOS DOS Mï¿½DULOS DE ATIVIDADES
     * 
     * @param converter
     * @param assignmentString
     * @param availableList
     */
    public void setAssignmentModulesData(DomainConverter converter, String assignmentString, HashMap availableList, int assignmentIndex) {
        int startIndex = assignmentString.indexOf("<" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">");
        int endIndex = assignmentString.indexOf("</" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">");
        if (startIndex == -1 || endIndex == -1) {
            return;
        }
        String moduleListString = assignmentString.substring(startIndex + IlmProtocol.ASSIGNMENT_MODULES_NODE.length() + 2, endIndex);
        String moduleString = "";
        Iterator availableListIterator = availableList.keySet().iterator();
        while (availableListIterator.hasNext()) {
            String key = (String) availableListIterator.next();
            startIndex = moduleListString.indexOf("<" + key);
            endIndex = moduleListString.indexOf("</" + key);
            if (endIndex != -1) {
                moduleString = moduleListString.substring(startIndex + key.length() + 2, endIndex);
            } else {
                moduleString = "";
            }
            if (availableList.get(key) instanceof AssignmentModule) {
                ((AssignmentModule) availableList.get(key)).setContentFromString(converter, assignmentIndex, moduleString);
            }
        }
    }
    
    public String getAssignmentModulesData(DomainConverter converter, String assignmentString, HashMap availableList, int assignmentIndex) {
        String string = "<" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">";
        Iterator availableListIterator = availableList.keySet().iterator();
        while (availableListIterator.hasNext()) {
            String key = (String) availableListIterator.next();
            if (availableList.get(key) instanceof AssignmentModule) {
                string += ((AssignmentModule) availableList.get(key)).getStringContent(converter, assignmentIndex);
            }
        }
        string += "</" + IlmProtocol.ASSIGNMENT_MODULES_NODE + ">";
        int index = assignmentString.lastIndexOf("</" + IlmProtocol.ASSIGNMENT_FILE_NODE + ">");
        return assignmentString.substring(0, index) + string + assignmentString.substring(index);
    }
}
