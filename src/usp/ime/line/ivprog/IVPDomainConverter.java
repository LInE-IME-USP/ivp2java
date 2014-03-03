package usp.ime.line.ivprog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import usp.ime.line.ivprog.model.domainaction.ChangeExpressionSign;
import usp.ime.line.ivprog.model.domainaction.ChangeForMode;
import usp.ime.line.ivprog.model.domainaction.ChangeValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableInitValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableName;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableType;
import usp.ime.line.ivprog.model.domainaction.CreateChild;
import usp.ime.line.ivprog.model.domainaction.CreateExpression;
import usp.ime.line.ivprog.model.domainaction.CreateVariable;
import usp.ime.line.ivprog.model.domainaction.DeleteExpression;
import usp.ime.line.ivprog.model.domainaction.DeleteVariable;
import usp.ime.line.ivprog.model.domainaction.ExpressionTypeChanged;
import usp.ime.line.ivprog.model.domainaction.MoveComponent;
import usp.ime.line.ivprog.model.domainaction.RemoveChild;
import usp.ime.line.ivprog.model.domainaction.UpdateReferencedVariable;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainConverter;

public class IVPDomainConverter implements DomainConverter {
    public Vector convertStringToObject(String objectListDescription) {
        return new Vector();
    }
    
    public String convertObjectToString(Vector objectList) {
        return "";
    }
    
    public Vector convertStringToAction(String actionListDescription) {
        Document doc = null;
        Vector actionList = new Vector();
        try {
            doc = loadXMLFromString("<gambi>" + actionListDescription + "</gambi>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("CARREGANDO ATIVIDADE... ><");
        Element node = doc.getDocumentElement();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            String value = currentNode.getNodeName().trim();
            NodeList nodes = currentNode.getChildNodes();
            HashMap parameters = new HashMap();
            if (!value.equals("#text")) {
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node cnode = nodes.item(j);
                    if (!cnode.getNodeName().equals("#text")) {
                        parameters.put(cnode.getNodeName(), cnode.getTextContent());
                    }
                }
                actionList.add(parseAction(value, parameters));
            }
        }
        return actionList;
    }
    
    private DomainAction parseAction(String name, HashMap parameters) {
        if (name.equals("changeexpressionsign")) {
            return parseChangeExpressionSign(parameters);
        } else if (name.equals("changeformode")) {
            return parseChangeForMode(parameters);
        } else if (name.equals("changevalue")) {
            return parseChangeValue(parameters);
        } else if (name.equals("changevariableinitvalue")) {
            return parseChangeVariableInitValue(parameters);
        } else if (name.equals("changevariablename")) {
            return parseChangeVariableName(parameters);
        } else if (name.equals("changevariabletype")) {
            return parseChangeVariableType(parameters);
        } else if (name.equals("createchild")) {
            return parseCreateChild(parameters);
        } else if (name.equals("createexpression")) {
            return parseCreateExpression(parameters);
        } else if (name.equals("createvariable")) {
            return parseCreateVariable(parameters);
        } else if(name.equals("deleteexpression")){
            return parseDeleteExpression(parameters);
        } else if(name.equals("deletevariable")){
            return parseDeleteVariable(parameters);
        } else if(name.equals("expressiontypechanged")){
            return parseExpressionTypeChanged(parameters);
        } else if(name.equals("movecomponent")){
            return parseMoveComponent(parameters);
        } else if(name.equals("removechild")){
            return parseRemoveChild(parameters);
        } else if(name.equals("updatereferencedvariable")){
            return parseUpdateReferencedVariable(parameters);
        }
        return null;
    }
    
    private DomainAction parseUpdateReferencedVariable(HashMap parameters) {
        UpdateReferencedVariable action = new UpdateReferencedVariable("updatereferencedvariable","updatereferencedvariable");
        action.setLastVarID((String) parameters.get("lastvarid"));
        action.setNewVarID((String) parameters.get("newvarid"));
        action.setReferenceID((String) parameters.get("referenceid"));
        return action;
    }

    private DomainAction parseRemoveChild(HashMap parameters) {
        RemoveChild action = new RemoveChild("removechild","removechild");
        action.setChildID((String) parameters.get("childid"));
        action.setContainerID((String) parameters.get("containerid"));
        action.setContext((String) parameters.get("context"));
        action.setIndex(Integer.parseInt((String)parameters.get("index")));
        return action;
    }

    private DomainAction parseMoveComponent(HashMap parameters) {
        MoveComponent action = new MoveComponent("movecomponent", "movecomponent");
        action.setComponent((String) parameters.get("component"));
        action.setDestiny((String) parameters.get("destiny"));
        action.setDestinyContext((String) parameters.get("destinycontext"));
        action.setOrigin((String) parameters.get("origin"));
        action.setOriginContext((String) parameters.get("origincontext"));
        action.setOriginIndex(Integer.parseInt((String)parameters.get("originindex")));
        action.setDropIndex(Integer.parseInt((String)parameters.get("dropindex")));
        return action;
    }

    private DomainAction parseExpressionTypeChanged(HashMap parameters) {
        ExpressionTypeChanged action = new ExpressionTypeChanged("expressiontypechanged","expressiontypechanged");
        action.setClean(Boolean.parseBoolean((String) parameters.get("isclean")));
        action.setComparison(Boolean.parseBoolean((String) parameters.get("iscomparison")));
        action.setContext((String) parameters.get("context"));
        action.setHolder((String) parameters.get("holder"));
        action.setExpression((String) parameters.get("expression"));
        return action;
    }

    private DomainAction parseDeleteVariable(HashMap parameters) {
        DeleteVariable action = new DeleteVariable("deletevariable", "deletevariable");
        action.setScopeID((String) parameters.get("scopeid"));
        action.setVariableID((String) parameters.get("variableid"));
        action.setIndex(Integer.parseInt((String) parameters.get("index")));
        return action;
    }

    private DomainAction parseDeleteExpression(HashMap parameters) {
        DeleteExpression action = new DeleteExpression("deleteexpression", "deleteexpression");
        action.setClean(Boolean.parseBoolean((String) parameters.get("isclean")));
        action.setComparison(Boolean.parseBoolean((String) parameters.get("iscomparison")));
        action.setContext((String) parameters.get("context"));
        action.setHolder((String) parameters.get("holder"));
        action.setExpression((String) parameters.get("expression"));
        return action;
    }

    private DomainAction parseCreateVariable(HashMap parameters) {
        CreateVariable action = new CreateVariable("createvariable", "createvariable");
        action.setVarID((String) parameters.get("varid"));
        action.setScopeID((String) parameters.get("scopeid"));
        action.setInitValue((String) parameters.get("initvalue"));
        return action;
    }
    
    private DomainAction parseCreateExpression(HashMap parameters) {
        CreateExpression action = new CreateExpression("createexpression", "createexpression");
        action.setHolder((String) parameters.get("holder"));
        action.setContext((String) parameters.get("context"));
        action.setExp1((String) parameters.get("lastexpression"));
        action.setExpressionType(Short.parseShort((String) parameters.get("expressiontype")));
        action.setPrimitiveType(Short.parseShort((String) parameters.get("primitivetype")));
        action.setRemovedExpression((String) parameters.get("removedexpression"));
        action.setNewExpression((String) parameters.get("newexpression"));
        return action;
    }
    
    private DomainAction parseCreateChild(HashMap parameters) {
        CreateChild action = new CreateChild("createchild", "createchild");
        action.setContainerID((String) parameters.get("containerid"));
        action.setScopeID((String) parameters.get("scopeid"));
        action.setObjectID((String) parameters.get("objectid"));
        action.setContext((String) parameters.get("context"));
        action.setClassID(Short.parseShort((String) parameters.get("classid")));
        action.setIndex(Integer.parseInt((String) parameters.get("index")));
        return action;
    }
    
    private DomainAction parseChangeVariableType(HashMap parameters) {
        ChangeVariableType action = new ChangeVariableType("changevariabletype", "changevariabletype");
        action.setNewType(Short.parseShort((String) parameters.get("newtype")));
        action.setLastType(Short.parseShort((String) parameters.get("newtype")));
        action.setVariableID((String) parameters.get("variableid"));
        // TODO: set returned vector
        return action;
    }
    
    private DomainAction parseChangeVariableName(HashMap parameters) {
        ChangeVariableName action = new ChangeVariableName("changevariablename", "changevariablename");
        action.setLastName((String) parameters.get("lastname"));
        action.setNewName((String) parameters.get("newname"));
        action.setVariableID((String) parameters.get("variableid"));
        return action;
    }
    
    private DomainAction parseChangeVariableInitValue(HashMap parameters) {
        ChangeVariableInitValue action = new ChangeVariableInitValue("changevariableinitvalue", "changevariableinitvalue");
        action.setVariableID((String) parameters.get("variableid"));
        action.setLastValue((String) parameters.get("lastvalue"));
        action.setNewValue((String) parameters.get("newvalue"));
        return action;
    }
    
    private DomainAction parseChangeValue(HashMap parameters) {
        ChangeValue action = new ChangeValue("changevalue", "changevalue");
        action.setId((String) parameters.get("id"));
        action.setLastValue((String) parameters.get("lastvalue"));
        action.setNewValue((String) parameters.get("newvalue"));
        action.setContext((String) parameters.get("context"));
        action.setHolder((String) parameters.get("holder"));
        return action;
    }
    
    private DomainAction parseChangeForMode(HashMap parameters) {
        ChangeForMode action = new ChangeForMode("changeformode", "changeformode");
        action.setLastMode(Integer.parseInt(parameters.get("lastmode").toString()));
        action.setForID((String) parameters.get("forid"));
        action.setNewMode(Integer.parseInt(parameters.get("newmode").toString()));
        return action;
    }
    
    private DomainAction parseChangeExpressionSign(HashMap parameters) {
        ChangeExpressionSign action = new ChangeExpressionSign("changeexpressionsign", "changeexpressionsign");
        action.setContext((String) parameters.get("context"));
        action.setExpressionID((String) parameters.get("expressionid"));
        action.setLastType(Short.parseShort(parameters.get("lasttype").toString()));
        action.setNewType(Short.parseShort(parameters.get("newtype").toString()));
        return action;
    }
    
    public String convertActionToString(Vector actionList) {
        String str = "";
        for (int i = 0; i < actionList.size(); i++) {
            str += actionList.get(i).toString();
        }
        return str;
    }
    
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
}
