package usp.ime.line.ivprog.controller;

import ilm.framework.domain.DomainModel;

import java.awt.event.ComponentListener;
import java.util.HashMap;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.domainaction.ChangeExpressionSign;
import usp.ime.line.ivprog.model.domainaction.ChangeValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableInitValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableName;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableType;
import usp.ime.line.ivprog.model.domainaction.CreateExpression;
import usp.ime.line.ivprog.model.domainaction.DeleteExpression;
import usp.ime.line.ivprog.model.domainaction.DeleteVariable;
import usp.ime.line.ivprog.model.domainaction.CreateChild;
import usp.ime.line.ivprog.model.domainaction.CreateVariable;
import usp.ime.line.ivprog.model.domainaction.MoveComponent;
import usp.ime.line.ivprog.model.domainaction.RemoveChild;
import usp.ime.line.ivprog.model.domainaction.UpdateReferencedVariable;
import usp.ime.line.ivprog.view.domaingui.IVPConsoleUI;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.utils.IVPMouseListener;

public class IVPController {

    private IVPProgram   program = null;
    private IVPDomainGUI gui     = null;
    private HashMap      actionList;
    private HashMap      codeListener;

    public IVPController() {
        actionList = new HashMap();
        codeListener = new HashMap();
    }
    
    public HashMap getCodeListener() {
        return codeListener;
    }

    public HashMap getActionList() {
        return actionList;
    }

    public IVPProgram getProgram() {
        return program;
    }

    public void setProgram(IVPProgram program) {
        this.program = program;
    }

    public IVPDomainGUI getGui() {
        return gui;
    }

    public void setGui(IVPDomainGUI gui) {
        this.gui = gui;
    }

    public void initializeModel() {
        program.initializeModel();
    }

    public void showExecutionEnvironment() {

    }

    public void showConstructionEnvironment() {

    }

    public void playCode() {
        program.playCode();
    }

    public void addChild(String containerID, short childType) {
        CreateChild newChild = (CreateChild) actionList.get("newchild");
        newChild.setClassID(childType);
        newChild.setContainerID(containerID);
        newChild.execute();
        ICodeListener listener = (ICodeListener) codeListener.get(containerID);
        listener.addChild(newChild.getObjectID());
    }
    
    public void moveChild(String child, String origin, String destiny, int dropIndex){
        MoveComponent mv = (MoveComponent) actionList.get("movecomponent");
        mv.setComponent(child);
        mv.setOrigin(origin);
        mv.setDestiny(destiny);
        mv.setDropY(dropIndex);
        mv.execute();
    }

    public void addParameter(String scopeID) {

    }

    public void addVariable(String scopeID, String initValue) {
        CreateVariable newVar = (CreateVariable) actionList.get("newvar");
        newVar.setScopeID(scopeID);
        newVar.setInitValue(initValue);
        newVar.execute();
    }

    public void deleteVariable(String scopeID, String id) {
        DeleteVariable delVar = (DeleteVariable) actionList.get("delvar");
        delVar.setScopeID(scopeID);
        delVar.setVariableID(id);
        delVar.execute();
    }

    public void changeVariableName(String id, String name) {
        ChangeVariableName changeVarName = (ChangeVariableName) actionList.get("changeVarName");
        changeVarName.setVariableID(id);
        changeVarName.setNewName(name);
        changeVarName.execute();
    }

    public void changeVariableType(String id, String type) {
        ChangeVariableType changeVarType = (ChangeVariableType) actionList.get("changeVarType");
        changeVarType.setVariableID(id);
        changeVarType.setNewType(type);
        changeVarType.execute();
    }

    public void updateVariableReference(String referenceID, String newReferencedVar) {
        UpdateReferencedVariable upVar = (UpdateReferencedVariable) actionList.get("updateReferencedVar");
        upVar.setReferenceID(referenceID);
        upVar.setNewVarID(newReferencedVar);
        upVar.execute();
    }

    public void changeVariableInitialValue(String id, String value) {
        ChangeVariableInitValue change = (ChangeVariableInitValue) actionList.get("changevariableinitvalue");
        change.setNewValue(value);
        change.setVariableID(id);
        change.execute();
    }

    public void changeValue(String id, String newValue) {
        ChangeValue chV = (ChangeValue) actionList.get("changevalue");
        chV.setId(id);
        chV.setNewValue(newValue);
        chV.execute();
    }

    public void createExpression(String leftExpID, String holder, short expressionType, String context) {
        CreateExpression createExpression = (CreateExpression) actionList.get("createexpression");
        createExpression.setExp1(leftExpID);
        createExpression.setHolder(holder);
        createExpression.setExpressionType(expressionType);
        createExpression.setContext(context);
        createExpression.execute();
    }

    public void deleteExpression(String id, String holder, String context, boolean isClean, boolean isComparison) {
        DeleteExpression deleteExpression = (DeleteExpression) actionList.get("deleteexpression");
        deleteExpression.setExpression(id);
        deleteExpression.setHolder(holder);
        deleteExpression.setContext(context);
        deleteExpression.setClean(isClean);
        deleteExpression.setComparison(isComparison);
        deleteExpression.execute();
    }

    public void changeExpressionSign(String id, short expressionType, String context) {
        ChangeExpressionSign changeExpression = (ChangeExpressionSign) actionList.get("changeexpressionsign");
        changeExpression.setExpressionID(id);
        changeExpression.setContext(context);
        changeExpression.setNewType(expressionType);
        changeExpression.execute();
    }
    
    public void removeChild(String containerID, String childID) {
        RemoveChild removeChild = (RemoveChild) actionList.get("removechild");
        removeChild.setChildID(childID);
        removeChild.setContainerID(containerID);
        removeChild.execute();
    }

    

    public void initDomainActionList(DomainModel model) {
        CreateVariable newVar = new CreateVariable("newvar", "newvar");
        newVar.setDomainModel(model);
        actionList.put("newvar", newVar);

        DeleteVariable delVar = new DeleteVariable("delvar", "delvar");
        delVar.setDomainModel(model);
        actionList.put("delvar", delVar);

        ChangeVariableName changeVarName = new ChangeVariableName("changeVarName", "changeVarName");
        changeVarName.setDomainModel(model);
        actionList.put("changeVarName", changeVarName);

        ChangeVariableType changeVarType = new ChangeVariableType("changeVarType", "changeVarType");
        changeVarType.setDomainModel(model);
        actionList.put("changeVarType", changeVarType);

        ChangeVariableInitValue change = new ChangeVariableInitValue("changevariableinitvalue", "changevariableinitvalue");
        change.setDomainModel(model);
        actionList.put("changevariableinitvalue", change);

        CreateChild newChild = new CreateChild("newchild", "newchild");
        newChild.setDomainModel(model);
        actionList.put("newchild", newChild);

        RemoveChild removeChild = new RemoveChild("removechild", "removechild");
        removeChild.setDomainModel(model);
        actionList.put("removechild", removeChild);

        CreateExpression createExpression = new CreateExpression("createexpression", "createexpression");
        createExpression.setDomainModel(model);
        actionList.put("createexpression", createExpression);

        DeleteExpression deleteExpression = new DeleteExpression("deleteexpression", "deleteexpression");
        deleteExpression.setDomainModel(model);
        actionList.put("deleteexpression", deleteExpression);

        ChangeExpressionSign changeExpressionSign = new ChangeExpressionSign("changeexpressionsign", "changeexpressionsign");
        changeExpressionSign.setDomainModel(model);
        actionList.put("changeexpressionsign", changeExpressionSign);

        UpdateReferencedVariable upVar = new UpdateReferencedVariable("updateReferencedVar", "updateReferencedVar");
        upVar.setDomainModel(model);
        actionList.put("updateReferencedVar", upVar);

        ChangeValue chV = new ChangeValue("changevalue", "changevalue");
        chV.setDomainModel(model);
        actionList.put("changevalue", chV);
        
        MoveComponent mv = new MoveComponent("movecomponent","movecomponent");
        mv.setDomainModel(model);
        actionList.put("movecomponent", mv);
    }

    public void addComponentListener(ICodeListener listener, String id) {
        codeListener.put(id, listener);
    }
    
    public void setConsole(IVPConsoleUI ivpConsoleUI) {
        program.setConsoleListener(ivpConsoleUI);
    }

    public void updateParent(String parentModelID, String currentModelID, String newExpID, String context) {
        program.updateParent(parentModelID, currentModelID, newExpID, context);
    }

   
};
