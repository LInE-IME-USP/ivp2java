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
import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Constant;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.For;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IfElse;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.ReadData;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Reference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.model.utils.IVPConstants;
import usp.ime.line.ivprog.view.domaingui.IVPConsoleUI;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameBoolean;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameDouble;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameInteger;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameString;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.AttributionLineUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.OperationUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.VariableSelectorUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends DomainModel {
    private HashMap             globalVariables     = null;
    private HashMap             preDefinedFunctions = null;
    private HashMap             functionMap         = null;
    private DataFactory         dataFactory         = null;
    private List                variableListeners;
    private List                functionListeners;
    private List                expressionListeners;
    private List                operationListeners;
    private Interpreter         interpreter;
    private String              currentScope        = "0";
    private AskUserFrameInteger readInteger;
    private AskUserFrameDouble  readDouble;
    private AskUserFrameString  readString;
    private AskUserFrameBoolean readBoolean;
    private HashMap             codeListeners;
    
    public IVPProgram() {
        globalVariables = new HashMap();
        preDefinedFunctions = new HashMap();
        codeListeners = new HashMap();
        functionMap = new HashMap();
        dataFactory = new DataFactory();
        variableListeners = new Vector();
        functionListeners = new Vector();
        expressionListeners = new Vector();
        operationListeners = new Vector();
        interpreter = new Interpreter();
        readInteger = new AskUserFrameInteger();
        readDouble = new AskUserFrameDouble();
        readString = new AskUserFrameString();
        readBoolean = new AskUserFrameBoolean();
        try {
            interpreter.set("readInteger", readInteger);
            interpreter.set("readDouble", readDouble);
            interpreter.set("readString", readString);
            interpreter.set("readBoolean", readBoolean);
        } catch (EvalError e) {
            e.printStackTrace();
        }
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
    
    public String newChild(String containerID, short classID, String context, AssignmentState state) {
        DataObject codeBlock = null;
        if (classID == IVPConstants.MODEL_WHILE) {
            codeBlock = (CodeComposite) dataFactory.createWhile();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_OPERATION_EQU, (short) -1, "while", state);
        } else if (classID == IVPConstants.MODEL_WRITE) {
            codeBlock = (DataObject) dataFactory.createPrint();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "printable", state);
        } else if (classID == IVPConstants.MODEL_IFELSE) {
            codeBlock = (DataObject) dataFactory.createIfElse();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_OPERATION_EQU, (short) -1, "ifElse", state);
        } else if (classID == IVPConstants.MODEL_ATTLINE) {
            codeBlock = (DataObject) dataFactory.createAttributionLine();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "leftVar", state);
        } else if (classID == IVPConstants.MODEL_READ) {
            codeBlock = (DataObject) dataFactory.createRead();
            initCodeBlock(containerID, codeBlock);
            createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "writable", state);
        } else if (classID == IVPConstants.MODEL_FOR) {
            codeBlock = (DataObject) dataFactory.createFor();
            initCodeBlock(containerID, codeBlock);
            createForExpressions((For) codeBlock, state);
        }
        CodeComposite container = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        container.addChild(codeBlock.getUniqueID());
        ICodeListener listener = (ICodeListener) codeListeners.get(containerID);
        listener.addChild(codeBlock.getUniqueID(), context);
        // Framework
        state.add(codeBlock);
        return codeBlock.getUniqueID();
    }
    
    private void createForExpressions(For codeBlock, AssignmentState state) {
        Expression increment = createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_INTEGER, Expression.EXPRESSION_INTEGER);
        Expression lowerBound = createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_INTEGER, Expression.EXPRESSION_INTEGER);
        String index = createForIndex(currentScope, "1", state);
        Variable var = (Variable) Services.getService().getModelMapping().get(index);
        codeBlock.setIncrementExpression(index);
        updateExpressionListeners(codeBlock.getUniqueID(), Expression.EXPRESSION_INTEGER, "forIncrement", state, increment);
        updateExpressionListeners(codeBlock.getUniqueID(), Expression.EXPRESSION_INTEGER, "forLowerBound", state, lowerBound);
        putExpressionOnRightPlace(codeBlock.getUniqueID(), "forIncrement", increment);
        putExpressionOnRightPlace(codeBlock.getUniqueID(), "forLowerBound", lowerBound);
    }
    
    private void initCodeBlock(String containerID, DataObject codeBlock) {
        codeBlock.setParentID(containerID);
        codeBlock.setScopeID(currentScope);
        Services.getService().getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
    }
    
    public int moveChild(String component, String origin, String destiny, String originContext, String destinyContext, int dropIndex, AssignmentState _currentState) {
        CodeComposite destinyCode = (CodeComposite) Services.getService().getModelMapping().get(destiny);
        CodeComposite originCode = (CodeComposite) Services.getService().getModelMapping().get(origin);
        CodeComponent componentCode = (CodeComponent) Services.getService().getModelMapping().get(component);
        int lastIndex = -1;
        ICodeListener destinyListener = (ICodeListener) Services.getService().getViewMapping().get(destiny);
        ICodeListener originListener = (ICodeListener) Services.getService().getViewMapping().get(origin);
        if (origin != destiny) {
            if (originCode instanceof IfElse) {
                if (originContext.equals("if")) {
                    lastIndex = originCode.removeChild(component);
                } else if (originContext.equals("else")) {
                    lastIndex = ((IfElse) originCode).removeElseChild(component);
                }
            } else {
                lastIndex = originCode.removeChild(component);
            }
            if (destinyCode instanceof IfElse) {
                if (destinyContext.equals("if")) {
                    destinyCode.addChildToIndex(component, dropIndex);
                } else if (destinyContext.equals("else")) {
                    ((IfElse) destinyCode).addElseChildToIndex(component, dropIndex);
                }
            } else {
                destinyCode.addChildToIndex(component, dropIndex);
            }
            originListener.childRemoved(component, originContext);
            destinyListener.restoreChild(component, dropIndex, destinyContext);
        } else {
            if (originCode instanceof IfElse) {
                if (originContext.equals("if")) {
                    lastIndex = originCode.removeChild(component);
                } else if (originContext.equals("else")) {
                    lastIndex = ((IfElse) originCode).removeElseChild(component);
                }
                if (destinyContext.equals("if")) {
                    destinyCode.addChildToIndex(component, dropIndex);
                } else if (destinyContext.equals("else")) {
                    ((IfElse) destinyCode).addElseChildToIndex(component, dropIndex);
                }
            } else {
                lastIndex = destinyCode.addChildToIndex(component, dropIndex);
                destinyListener.restoreChild(component, dropIndex, destinyContext);
            }
            originListener.childRemoved(component, originContext);
            destinyListener.restoreChild(component, dropIndex, destinyContext);
        }
        componentCode.setParentID(destiny);
        return lastIndex;
    }
    
    public int removeChild(String containerID, String childID, String context, AssignmentState state) {
        CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        int index = 0;
        index = parent.removeChild(childID);
        ICodeListener codeListener = (ICodeListener) codeListeners.get(containerID);
        codeListener.childRemoved(childID, context);
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
    
    public void restoreChild(String containerID, String childID, int index, String context, AssignmentState state) {
        CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
        parent.addChildToIndex(childID, index);
        ICodeListener codeListener = (ICodeListener) codeListeners.get(containerID);
        codeListener.restoreChild(childID, index, context);
    }
    
    // Function actions
    public void createParameter(String scopeID) {
    }
    
    public void removeParameter(String scopeID, String parameterID) {
    }
    
    public String createVariable(String scopeID, String initialValue, AssignmentState state) {
        Function f = (Function) Services.getService().getModelMapping().get(scopeID);
        Variable newVar = (Variable) dataFactory.createVariable();
        newVar.setVariableName("variavel" + f.getVariableCount());
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
    
    public String createForIndex(String scopeID, String initialValue, AssignmentState state) {
        Function f = (Function) Services.getService().getModelMapping().get(scopeID);
        Variable newVar = (Variable) dataFactory.createVariable();
        newVar.setVariableName("#@ivprog@#!index"+For.getForCount());
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
    
    public String createExpression(String leftExpID, String holder, short expressionType, short primitiveType, String context, AssignmentState state) {
        System.out.println("criou uma expressao: contextO " + context);
        Expression exp = createExpression(leftExpID, holder, expressionType, primitiveType);
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
        } else if (context.equals("writable")) {
            ((ReadData) Services.getService().getModelMapping().get(holder)).setWritableObject(exp.getUniqueID());
        } else if (context.equals("while")) {
            ((While) Services.getService().getModelMapping().get(holder)).setCondition(exp.getUniqueID());
        } else if (context.equals("ifElse")) {
            ((IfElse) Services.getService().getModelMapping().get(holder)).setComparison(exp.getUniqueID());
        } else if (context.equals("forIndex")) {
            ((For) Services.getService().getModelMapping().get(holder)).setIndexExpression(exp.getUniqueID());
        } else if (context.equals("forLowerBound")) {
            ((For) Services.getService().getModelMapping().get(holder)).setLowerBoundExpression(exp.getUniqueID());
        } else if (context.equals("forUpperBound")) {
            ((For) Services.getService().getModelMapping().get(holder)).setUpperBoundExpression(exp.getUniqueID());
        } else if (context.equals("forIncrement")) {
            ((For) Services.getService().getModelMapping().get(holder)).setIncrementExpression(exp.getUniqueID());
        }
    }
    
    private Expression createExpression(String leftExpID, String holder, short expressionType, short primitiveType) {
        Expression exp = null;
        if (expressionType == Expression.EXPRESSION_VARIABLE) {
            exp = (Expression) dataFactory.createVarReference();
            exp.setExpressionType(Expression.EXPRESSION_VARIABLE);
            ((VariableReference) exp).setReferencedType(primitiveType);
        } else if (expressionType >= Expression.EXPRESSION_INTEGER && expressionType <= Expression.EXPRESSION_BOOLEAN) {
            exp = (Expression) dataFactory.createConstant();
            exp.setExpressionType(expressionType);
            ((Constant) exp).setConstantValue(getInitvalue(expressionType));
        } else {
            System.out.println("identificou que era uma expressao do tipo operação; contexto: ");
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
            ((AttributionLine) dataHolder).setRightExpression("");
            lastExpressionID = null;
        } else if (dataHolder instanceof Print) {
            lastExpressionID = null;
        } else if (dataHolder instanceof Operation) {
            ((Operation) dataHolder).removeExpression(expression);
            lastExpressionID = ((Operation) dataHolder).getExpressionA();
        } else if (dataHolder instanceof For) {
            System.out.println("encontrou que era pra remover do For");
            ((For) dataHolder).removeExpression(expression, context);
        }
        for (int i = 0; i < expressionListeners.size(); i++) {
            ((IExpressionListener) expressionListeners.get(i)).expressionDeleted(expression, context, isClean);
        }
        // checar aqui...
        if (isClean) {
            Expression newExp;
            if (!isComparison) {
                newExp = (Expression) dataFactory.createVarReference();
                newExp.setExpressionType(Expression.EXPRESSION_VARIABLE);
                if (dataHolder instanceof For) {
                    ((VariableReference) newExp).setReferencedType(Expression.EXPRESSION_INTEGER);
                }else{
                    ((VariableReference) newExp).setReferencedType(((AttributionLine) dataHolder).getLeftVariableType());
                }
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
            if (state != null)
                state.add(newExp);
        }
        if (state != null)
            state.remove(exp);
        return expression;
    }
    
    public void restoreExpression(String expression, String holder, String context, boolean wasClean, AssignmentState state) {
        Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
        for (int i = 0; i < expressionListeners.size(); i++) {
            if (wasClean) {
                ((IExpressionListener) expressionListeners.get(i)).expressionRestoredFromCleaning(holder, expression, context);
            } else {
                ((IExpressionListener) expressionListeners.get(i)).expressionRestored(holder, expression, context);
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
        IValueListener v = (IValueListener) Services.getService().getViewMapping().get(id);
        v.valueChanged(constantValue);
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
    
    public Vector changeVariableType(String id, short newType, AssignmentState state) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        short lastType = v.getVariableType();
        Vector ret = new Vector();
        ret.add(lastType);
        v.setVariableType(newType);
        v.setVariableValue(getInitvalue(newType));
        Vector attLines = new Vector();
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.changeVariableType(id, newType);
            if (listener instanceof VariableSelectorUI) {
                if (((VariableSelectorUI) listener).isIsolated()) {
                    String modelParentID = ((VariableSelectorUI) listener).getModelParent();
                    if (Services.getService().getModelMapping().get(modelParentID) instanceof AttributionLine) {
                        attLines.add(modelParentID);
                    }
                }
            }
        }
        for (int i = 0; i < attLines.size(); i++) { // ta errado... só posso mexer na attLine se eu estiver mostrando (na ref da esquerda) a var que mudou
            AttributionLine attLine = (AttributionLine) Services.getService().getModelMapping().get(attLines.get(i));
            VariableReference varRef = (VariableReference) Services.getService().getModelMapping().get(attLine.getLeftVariableID());
            if (attLine.getLeftVariableType() != newType && id.equals(varRef.getReferencedVariable())) {
                state.remove((DomainObject) Services.getService().getModelMapping().get(attLine.getRightExpressionID()));
                attLine.setLeftVariableType(newType);
                AttributionLineUI attLineUI = (AttributionLineUI) Services.getService().getViewMapping().get(attLines.get(i));
                attLineUI.updateHoldingType(newType);
                ret.add(deleteExpression(attLine.getRightExpressionID(), attLine.getUniqueID(), "", true, false, state));
            }
        }
        state.updateState(v);
        return ret;
    }
    
    public void restoreVariableType(String id, Vector ret, AssignmentState state) {
        Variable v = (Variable) Services.getService().getModelMapping().get(id);
        v.setVariableType((Short) ret.get(0));
        v.setVariableValue(getInitvalue((Short) ret.get(0)));
        Vector attLines = new Vector();
        for (int i = 0; i < variableListeners.size(); i++) {
            IVariableListener listener = (IVariableListener) variableListeners.get(i);
            listener.changeVariableType(id, (Short) ret.get(0));
            if (listener instanceof VariableSelectorUI) {
                if (((VariableSelectorUI) listener).isIsolated()) {
                    String modelParentID = ((VariableSelectorUI) listener).getModelParent();
                    if (Services.getService().getModelMapping().get(modelParentID) instanceof AttributionLine) {
                        attLines.add(modelParentID);
                    }
                }
            }
        }
        for (int i = 0; i < attLines.size(); i++) {
            AttributionLine attLine = (AttributionLine) Services.getService().getModelMapping().get(attLines.get(i));
            VariableReference varRef = (VariableReference) Services.getService().getModelMapping().get(attLine.getLeftVariableID());
            if (attLine.getLeftVariableType() != (Short) ret.get(0) && id.equals(varRef.getReferencedVariable())) {
                attLine.setLeftVariableType((Short) ret.get(0));
                AttributionLineUI attLineUI = (AttributionLineUI) Services.getService().getViewMapping().get(attLines.get(i));
                attLineUI.updateHoldingType((Short) ret.get(0));
            }
        }
        for (int i = 1; i < ret.size(); i++) {
            String restoredID = (String) ret.get(i);
            String holderID = ((Expression) Services.getService().getModelMapping().get(ret.get(i))).getParentID();
            restoreExpression(restoredID, holderID, "", true, state);
            state.add((DomainObject) Services.getService().getModelMapping().get((String) ret.get(i)));
        }
        state.updateState(v);
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
        DataObject parent = (DataObject) Services.getService().getModelMapping().get(parentModelID);
        if (parent instanceof CodeComponent) {
            ((CodeComponent) parent).updateParent(currentModelID, newExp, operationContext);
        } else if (parent instanceof Operation) {
            ((Operation) parent).updateParent(currentModelID, newExp, operationContext);
        }
    }
    
    public short updateAttLineType(String attLineID, short newType) {
        AttributionLine attLine = (AttributionLine) Services.getService().getModelMapping().get(attLineID);
        short lastType = attLine.getLeftVariableType();
        attLine.setLeftVariableType(newType);
        deleteExpression(attLine.getRightExpressionID(), attLineID, "", true, false, null);
        ((AttributionLineUI) Services.getService().getViewMapping().get(attLineID)).updateHoldingType(newType);
        return lastType;
    }
    
    public String getInitvalue(short type) {
        if (type == Expression.EXPRESSION_BOOLEAN) {
            return "true";
        } else if (type == Expression.EXPRESSION_DOUBLE) {
            return "1.0";
        } else if (type == Expression.EXPRESSION_INTEGER) {
            return "1";
        } else if (type == Expression.EXPRESSION_STRING) {
            return ResourceBundleIVP.getString("helloWorld.text");
        }
        return "";
    }
    
    public void addComponentListener(ICodeListener listener, String id) {
        codeListeners.put(id, listener);
    }
    
    public HashMap getCodeListener() {
        return codeListeners;
    }
    
    public void playCode() {
        String code = " ";
        Object[] functionList = functionMap.values().toArray();
        for (int i = 0; i < functionList.length; i++) {
            code += " " + ((Function) functionList[i]).toJavaString() + " ";
        }
        code += " Principal(); ";
        String finalCode = "Runnable r = new Runnable(){ public void run() {" + code + "} }; Thread t = new Thread(r); t.run();";
        System.out.println(finalCode);
        try {
            interpreter.eval(finalCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
