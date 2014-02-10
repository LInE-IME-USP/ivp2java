package usp.ime.line.ivprog.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import bsh.ConsoleInterface;
import bsh.EvalError;
import bsh.Interpreter;
import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.listeners.IOperationListener;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Constant;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Reference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.model.utils.IVPConstants;
import usp.ime.line.ivprog.view.domaingui.IVPConsoleUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends DomainModel {

    private HashMap     globalVariables     = null;
    private HashMap     preDefinedFunctions = null;
    private HashMap     functionMap         = null;
    private DataFactory dataFactory         = null;
    private List        variableListeners;
    private List        functionListeners;
    private List        expressionListeners;
    private List        operationListeners;
    private Interpreter interpreter;

    private String      currentScope        = "0";

    public IVPProgram() {
        globalVariables = new HashMap();
        preDefinedFunctions = new HashMap();
        functionMap = new HashMap();
        dataFactory = new DataFactory();
        variableListeners = new Vector();
        functionListeners = new Vector();
        expressionListeners = new Vector();
        operationListeners = new Vector();
        interpreter = new Interpreter();
    }

    public void initializeModel() {
        createFunction(ResourceBundleIVP.getString("mainFunctionName"), IVPConstants.FUNC_RETURN_VOID, null);
    }

    // Program actions
    public void createFunction(String name, String funcReturnVoid, AssignmentState state) {
        Function f = (Function) dataFactory.createFunction();
        f.setFunctionName(name);
        f.setReturnType(funcReturnVoid);
        functionMap.put(name, f);
        Services.getService().getModelMapping().put(f.getUniqueID(), f);
        for (int i = 0; i < functionListeners.size(); i++) {
            IFunctionListener listener = (IFunctionListener) functionListeners.get(i);
            listener.functionCreated(f.getUniqueID());
        }
        if (state != null)
            state.add(f);
    }

    public void removeFunction(String name, AssignmentState state) {
        // Framework
        state.remove((DomainObject) functionMap.get(name));
        functionMap.remove(name);
    }

    public String newChild(String containerID, short classID, AssignmentState state) {
        DataObject codeBlock = null;
        if (classID == IVPConstants.MODEL_WHILE) {
            codeBlock = (CodeComposite) dataFactory.createWhile();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_OPERATION_EQU, "while", state);
        } else if (classID == IVPConstants.MODEL_WRITE) {
            codeBlock = (DataObject) dataFactory.createPrint();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, "printable", state);
        } else if (classID == IVPConstants.MODEL_ATTLINE) {
            codeBlock = (DataObject) dataFactory.createAttributionLine();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, "leftVar", state);
        }
        CodeComposite container = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        container.addChild(codeBlock.getUniqueID());
        // Framework
        state.add(codeBlock);
        return codeBlock.getUniqueID();
    }

    private void initCodeBlock(String containerID, DataObject codeBlock) {
        codeBlock.setParentID(containerID);
        codeBlock.setScopeID(currentScope);
        Services.getService().getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
    }

    public int moveChild(String component, String origin, String destiny, int dropIndex, AssignmentState _currentState) {
        CodeComposite destinyCode = (CodeComposite) Services.getService().getModelMapping().get(destiny);
        CodeComposite originCode = (CodeComposite) Services.getService().getModelMapping().get(origin);
        CodeComponent componentCode = (CodeComponent) Services.getService().getModelMapping().get(component);
        int lastIndex;
        ICodeListener destinyListener = (ICodeListener) Services.getService().getViewMapping().get(destiny);
        ICodeListener originListener = (ICodeListener) Services.getService().getViewMapping().get(origin);
        if (origin != destiny) {
            lastIndex = originCode.removeChild(component);
            destinyCode.addChildToIndex(component, dropIndex);
            originListener.childRemoved(component);
            destinyListener.restoreChild(component, dropIndex);
        } else {
            lastIndex = destinyCode.addChildToIndex(component, dropIndex);
            destinyListener.restoreChild(component, dropIndex);
        }
        componentCode.setParentID(destiny);
        return lastIndex;
    }

    public int removeChild(String containerID, String childID, AssignmentState state) {
        CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        int index = 0;
        index = parent.removeChild(childID);
        ICodeListener codeListener = (ICodeListener) Services.getService().getController().getCodeListener().get(containerID);
        codeListener.childRemoved(childID);
        // Check child dependencies and remove them
        CodeComponent child = (CodeComponent) Services.getService().getModelMapping().get(childID);
        if (child instanceof AttributionLine) {
            state.remove((DomainObject) Services.getService().getModelMapping().get(((AttributionLine) child).getLeftVariableID()));
        } else if (child instanceof CodeComposite) {
            // remove children recursively
        }
        // Framework
        state.remove((DomainObject) Services.getService().getModelMapping().get(childID));
        return index;
    }

    public void restoreChild(String containerID, String childID, int index, AssignmentState state) {
        CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        parent.addChildToIndex(childID, index);
        ICodeListener codeListener = (ICodeListener) Services.getService().getController().getCodeListener().get(containerID);
        codeListener.restoreChild(childID, index);
    }

    // Function actions
    public void createParameter(String scopeID) {

    }

    public void removeParameter(String scopeID, String parameterID) {

    }

    public String createVariable(String scopeID, String initialValue, AssignmentState state) {
        Function f = (Function) Services.getService().getModelMapping().get(scopeID);
        Variable newVar = (Variable) dataFactory.createVariable();
        newVar.setVariableName("newVar" + f.getVariableCount());
        newVar.setVariableType(Expression.EXPRESSION_INTEGER);
        newVar.setVariableValue(initialValue);
        newVar.setScopeID(currentScope);
        Services.getService().getModelMapping().put(newVar.getUniqueID(), newVar);
        f.addLocalVariable(newVar.getUniqueID());
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.addedVariable(newVar.getUniqueID());
        }
        state.add(newVar);
        return newVar.getUniqueID();
    }

    public void removeVariable(String scopeID, String variableID, AssignmentState state) {
        Function f = (Function) Services.getService().getModelMapping().get(scopeID);
        f.removeLocalVariable(variableID);
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.removedVariable(variableID);
        }
        state.remove((DomainObject) Services.getService().getModelMapping().get(variableID));
    }

    public void restoreVariable(String scopeID, String variableID, AssignmentState state) {
        Function f = (Function) Services.getService().getModelMapping().get(scopeID);
        f.addLocalVariable(variableID);
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.variableRestored(variableID);
        }
        state.add((DomainObject) Services.getService().getModelMapping().get(variableID));
    }

    public String updateReferencedVariable(String refID, String newVarRef, AssignmentState state) {
        String lastReferencedVariable = "";
        VariableReference ref = (VariableReference) Services.getService().getModelMapping().get(refID);
        lastReferencedVariable = ref.getReferencedVariable();
        ref.setReferencedVariable(newVarRef);
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.updateReference(refID);
        }

        if (newVarRef != null) {
            // significa que estou voltando ao estado inicial.
            Variable newReferenced = (Variable) Services.getService().getModelMapping().get(newVarRef);
            newReferenced.addVariableReference(refID);
        }

        Variable lastReferenced = (Variable) Services.getService().getModelMapping().get(lastReferencedVariable);
        if (lastReferenced != null && !"".equals(lastReferenced)) {
            lastReferenced.removeVariableReference(refID);
        }
        return lastReferencedVariable;
    }
    public String createExpression(String leftExpID, String holder, short expressionType, String context, AssignmentState state) {
        Expression exp = createExpression(leftExpID, holder, expressionType);
        updateExpressionListeners(holder, expressionType, context, state, exp);
        putExpressionOnRightPlace(holder, context, exp);
        state.add(exp);
        return exp.getUniqueID();
    }

    private void updateExpressionListeners(String holder, short expressionType, String context, AssignmentState state, Expression exp) {
        for (int i = 0; i < expressionListeners.size(); i++) {
            ((IExpressionListener) expressionListeners.get(i)).expressionCreated(holder, exp.getUniqueID(), context);
        }
        if (expressionType == Expression.EXPRESSION_OPERATION_AND || expressionType == Expression.EXPRESSION_OPERATION_OR) {
            Expression newExp = (Expression) dataFactory.createExpression();
            newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
            newExp.setParentID(exp.getUniqueID());
            newExp.setScopeID(currentScope);
            ((Operation) exp).setExpressionB(newExp.getUniqueID());
            Services.getService().getModelMapping().put(newExp.getUniqueID(), newExp);
            for (int i = 0; i < expressionListeners.size(); i++) {
                ((IExpressionListener) expressionListeners.get(i)).expressionCreated(exp.getUniqueID(), newExp.getUniqueID(), "right");
            }
            state.add(newExp);
        }
    }

    private void putExpressionOnRightPlace(String holder, String context, Expression exp) {
        if (context.equals("right")) {
            ((Operation) Services.getService().getModelMapping().get(holder)).setExpressionB(exp.getUniqueID());
        } else if (context.equals("left")) {
            ((Operation) Services.getService().getModelMapping().get(holder)).setExpressionA(exp.getUniqueID());
        } else if (context.equals("leftVar")) {
            ((AttributionLine) Services.getService().getModelMapping().get(holder)).setLeftVariableID(exp.getUniqueID());
        } else if (context.equals("printable")) {
            ((Print) Services.getService().getModelMapping().get(holder)).setPrintableObject(exp.getUniqueID());
        } else if (context.equals("while")) {
            ((While) Services.getService().getModelMapping().get(holder)).setCondition(exp.getUniqueID());
        }
    }

    private Expression createExpression(String leftExpID, String holder, short expressionType) {
        Expression exp = null;
        if (expressionType == Expression.EXPRESSION_VARIABLE) {
            exp = (Expression) dataFactory.createVarReference();
            exp.setExpressionType(expressionType);
        } else if (expressionType >= Expression.EXPRESSION_INTEGER && expressionType <= Expression.EXPRESSION_BOOLEAN) {
            exp = (Expression) dataFactory.createConstant();
            exp.setExpressionType(expressionType);
            ((Constant) exp).setConstantValue(getInitvalue(expressionType));
        } else {
            exp = (Expression) dataFactory.createExpression();
            exp.setExpressionType(expressionType);
            if (leftExpID != "") {
                ((Expression) Services.getService().getModelMapping().get(leftExpID)).setParentID(exp.getUniqueID());
                ((Operation) exp).setExpressionA(leftExpID);
            }
        }
        exp.setParentID(holder);
        exp.setScopeID(currentScope);
        Services.getService().getModelMapping().put(exp.getUniqueID(), exp);
        return exp;
    }

    public String deleteExpression(String expression, String holder, String context, boolean isClean, boolean isComparison, AssignmentState state) {
        Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
        DataObject dataHolder = (DataObject) Services.getService().getModelMapping().get(holder);
        String lastExpressionID;
        if (dataHolder instanceof AttributionLine) {
            ((AttributionLine) dataHolder).setRightExpression(null);
            lastExpressionID = null;
        } else if (dataHolder instanceof Print) {
            lastExpressionID = null;
        } else if (dataHolder instanceof Operation) {
            ((Operation) dataHolder).removeExpression(expression);
            lastExpressionID = ((Operation) dataHolder).getExpressionA();
        }
        for (int i = 0; i < expressionListeners.size(); i++) {
            ((IExpressionListener) expressionListeners.get(i)).expressionDeleted(expression, context, isClean);
        }
        if (isClean) {
            Expression newExp;
            if (!isComparison) {
                newExp = (Expression) dataFactory.createVarReference();
                newExp.setExpressionType(Expression.EXPRESSION_VARIABLE);
            } else {
                newExp = (Expression) dataFactory.createExpression();
                newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
            }
            newExp.setParentID(holder);
            newExp.setScopeID(currentScope);
            Services.getService().getModelMapping().put(newExp.getUniqueID(), newExp);
            for (int i = 0; i < expressionListeners.size(); i++) {
                ((IExpressionListener) expressionListeners.get(i)).expressionCreated(holder, newExp.getUniqueID(), context);
            }
            state.add(newExp);
        }
        state.remove(exp);
        return expression;
    }

    public void restoreExpression(String expression, String holder, String context, boolean wasClean, AssignmentState state) {
        Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
        for (int i = 0; i < expressionListeners.size(); i++) {
            if (wasClean) {
                ((IExpressionListener) expressionListeners.get(i)).expressionRestoredFromCleaning(holder, exp.getUniqueID(), context);
            } else {
                ((IExpressionListener) expressionListeners.get(i)).expressionRestored(holder, exp.getUniqueID(), context);
            }

        }
        state.add(exp);
    }

    public short changeOperationSign(String expression, String context, short newType, AssignmentState state) {
        Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
        short lastType = exp.getExpressionType();
        exp.setExpressionType(newType);
        for (int i = 0; i < operationListeners.size(); i++) {
            ((IOperationListener) operationListeners.get(i)).operationTypeChanged(expression, context);
        }
        return lastType;
    }

    public String changeValue(String id, String constantValue, AssignmentState state) {
        Constant c = (Constant) Services.getService().getModelMapping().get(id);
        String lastValue = c.getConstantValue();
        c.setConstantValue(constantValue);
        return lastValue;
    }

    public void addExpressionListener(IExpressionListener listener) {
        if (!expressionListeners.contains(listener)) {
            expressionListeners.add(listener);
        }
    }

    public void addOperationListener(IOperationListener listener) {
        if (!operationListeners.contains(listener)) {
            operationListeners.add(listener);
        }
    }

    public void addVariableListener(IVariableListener listener) {
        variableListeners.add(listener);
    }

    public void addFunctionListener(IFunctionListener listener) {
        functionListeners.add(listener);
    }

    public String changeVariableName(String id, String name, AssignmentState state) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        String lastName = v.getVariableName();
        v.setVariableName(name);
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.changeVariableName(id, name, lastName);
        }
        for (int i = 0; i < v.getVariableReferenceList().size(); i++) {
            Reference r = (Reference) Services.getService().getModelMapping().get(v.getVariableReferenceList().get(i));
            r.setReferencedName(v.getVariableName());
        }
        state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
        return lastName;
    }

    public short changeVariableType(String id, short newType, AssignmentState state) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        short lastType = v.getVariableType();
        v.setVariableType(newType);
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.changeVariableType(id, newType);
        }
        state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
        return lastType;
    }

    public String changeVariableInitialValue(String id, String value, AssignmentState state) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        String lastValue = v.getVariableValue();
        v.setVariableValue(value);

        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.changeVariableValue(id, value);
        }
        state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
        return lastValue;
    }

    public AssignmentState getNewAssignmentState() {
        return new AssignmentState();
    }

    public float AutomaticChecking(AssignmentState studentAnswer, AssignmentState expectedAnswer) {
        return 0;
    }

    public void setConsoleListener(IVPConsoleUI ivpConsoleUI) {
        interpreter.setConsole(ivpConsoleUI);
    }

    public void updateParent(String parentModelID, String currentModelID, String newExp, String operationContext) {
        ((CodeComponent) Services.getService().getModelMapping().get(parentModelID)).updateParent(currentModelID, newExp, operationContext);
    }

    public String getInitvalue(short type) {
        if (type == Expression.EXPRESSION_BOOLEAN) {
            return "true";
        } else if (type == Expression.EXPRESSION_DOUBLE) {
            return "1.0";
        } else if (type == Expression.EXPRESSION_INTEGER) {
            return "1";
        } else if (type == Expression.EXPRESSION_STRING) {
            return "Hello, world!";
        }
        return "";
    }

    public void addComponentListener() {

    }

    public void playCode() {
        String code = " ";
        Object[] functionList = functionMap.values().toArray();
        for (int i = 0; i < functionList.length; i++) {
            code += " " + ((Function) functionList[i]).toJavaString() + " ";
        }
        code += " Principal(); ";
        try {
            interpreter.eval(code);
        } catch (Exception e) {
        }
    }
}
