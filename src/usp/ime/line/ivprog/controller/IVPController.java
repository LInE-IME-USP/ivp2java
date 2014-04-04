package usp.ime.line.ivprog.controller;

import ilm.framework.domain.DomainModel;

import java.awt.Cursor;
import java.util.HashMap;

import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
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
import usp.ime.line.ivprog.model.domainaction.MoveComponent;
import usp.ime.line.ivprog.model.domainaction.RemoveChild;
import usp.ime.line.ivprog.model.domainaction.UpdateReferencedVariable;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.view.domaingui.IVPConsole;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;

public class IVPController {
    private IVPProgram   program          = null;
    private IVPDomainGUI currentDomainGUI = null;
    
    public HashMap getActionList() {
        return currentDomainGUI.getActionList();
    }
    
    public IVPProgram getProgram() {
        return program;
    }
    
    public void setProgram(IVPProgram program) {
        this.program = program;
    }
    
    public IVPDomainGUI getGui() {
        return currentDomainGUI;
    }
    
    public void setGui(IVPDomainGUI gui) {
        this.currentDomainGUI = gui;
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
    
    public void addChild(String containerID, short childType, String context) {
        currentDomainGUI.addChild(containerID, childType, context);
    }
    
    public void removeChild(String containerID, String childID, String context) {
        currentDomainGUI.removeChild(containerID, childID, context);
    }
    
    public void moveChild(String child, String origin, String destiny, String originContext, String destinyContext, int dropIndex) {
        currentDomainGUI.moveChild(child, origin, destiny, originContext, destinyContext, dropIndex);
    }
    
    public void addParameter(String scopeID) {
    }
    
    public void addVariable(String scopeID, String initValue) {
        currentDomainGUI.addVariable(scopeID, initValue);
    }
    
    public void deleteVariable(String scopeID, String id) {
        currentDomainGUI.deleteVariable(scopeID, id);
    }
    
    public void changeVariableName(String id, String name) {
        currentDomainGUI.changeVariableName(id, name);
    }
    
    public void changeVariableType(String id, short expressionInteger) {
        currentDomainGUI.changeVariableType(id, expressionInteger);
    }
    
    public void updateVariableReference(String referenceID, String newReferencedVar) {
        currentDomainGUI.updateVariableReference(referenceID, newReferencedVar);
    }
    
    public void changeVariableInitialValue(String id, String value) {
        currentDomainGUI.changeVariableInitialValue(id, value);
    }
    
    public void changeValue(String id, String newValue) {
        currentDomainGUI.changeValue(id, newValue);
    }
    
    public void createExpression(String leftExpID, String holder, short expressionType, short primitiveType, String context) {
        currentDomainGUI.createExpression(leftExpID, holder, expressionType, primitiveType, context);
    }
    
    public void deleteExpression(String id, String holder, String context, boolean isClean, boolean isComparison) {
        currentDomainGUI.deleteExpression(id, holder, context, isClean, isComparison);
    }
    
    public void changeExpressionSign(String id, short expressionType, String context) {
        currentDomainGUI.changeExpressionSign(id, expressionType, context);
    }
    
    public void initDomainActionList(DomainModel model) {
    }
    
    public void addComponentListener(ICodeListener listener, String id) {
        program.addComponentListener(listener, id);
    }
    
    public void setConsole(IVPConsole ivpConsoleUI) {
        program.setConsoleListener(ivpConsoleUI);
    }
    
    public void updateParent(String parentModelID, String currentModelID, String newExpID, String context) {
        program.updateParent(parentModelID, currentModelID, newExpID, context);
    }
    
    public void showConfigurationsWindow() {
    }
    
    public void changeCursor(int cursor) {
        currentDomainGUI.setCursor(Cursor.getPredefinedCursor(cursor));
    }
    
    public void changeCursor(Cursor cursor) {
        currentDomainGUI.setCursor(cursor);
    }
    
    public void changeInteractionProtocol(String interactionProtocol) {
        Services.getService().getML().setProtocol(interactionProtocol);
    }
    
    public void updateAttLineType(String attLineID, short newType) {
        program.updateAttLineType(attLineID, newType);
    }
    
    public void changeForMode(int forMode, String modelID) {
        currentDomainGUI.changeForMode(forMode, modelID);
    }
    
    public void printError(String errorMessage) {
        program.printError(errorMessage);
    }
    
    public boolean validateVariableName(String modelScopeID, String value) {
        return program.validateVariableName(modelScopeID, value);
    }
    
    public boolean isContentSet() {
        // TODO: aqui dá pra criar uma mensagem pra olhar quais funções deverão ter os campos verificados.
        boolean isCSet = true;
        Object[] functions = program.getFunctionMap().values().toArray();
        for (int i = 0; i < functions.length; i++) {
            FunctionBodyUI f = (FunctionBodyUI) Services.getService().getViewMapping().get(((Function) functions[i]).getUniqueID());
            if (!f.checkContentSet()) {
                if (isCSet) {
                    isCSet = false;
                }
            }
        }
        return isCSet;
    }
    
    public void lockCodeDown() {
        Object[] functions = program.getFunctionMap().values().toArray();
        for (int i = 0; i < functions.length; i++) {
            FunctionBodyUI f = (FunctionBodyUI) Services.getService().getViewMapping().get(((Function) functions[i]).getUniqueID());
            f.lockCodeDown();
        }
    }
}
