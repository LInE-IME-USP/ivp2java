
/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bsh.ConsoleInterface;
import bsh.EvalError;
import bsh.Interpreter;
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
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.view.domaingui.IVPConsole;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameBoolean;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameDouble;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameInteger;
import usp.ime.line.ivprog.view.domaingui.frames.AskUserFrameString;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.AttributionLineUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.OperationUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.VariableSelectorUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends DomainModel {
  
  private Interpreter interpreter;
  private String currentScope = "0";
  private AskUserFrameInteger readInteger;
  private AskUserFrameDouble readDouble;
  private AskUserFrameString readString;
  private AskUserFrameBoolean readBoolean;
  private IVPConsole console;

  public IVPProgram () {
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

  public void initializeModel () {
    createFunction(ResourceBundleIVP.getString("mainFunctionName"), IVPConstants.FUNC_RETURN_VOID, Services
            .getCurrentState());
    }

  // Program actions
  public void createFunction (String name, String funcReturnVoid, AssignmentState state) {
    Function f = (Function) state.getData().getDataFactory().createFunction();
    f.setFunctionName(name);
    f.setReturnType(funcReturnVoid);
    state.getData().getFunctionMap().put(name, f);
    Services.getModelMapping().put(f.getUniqueID(), f);
    for (int i = 0; i < state.getData().getFunctionListeners().size(); i++) {
      IFunctionListener listener = (IFunctionListener) state.getData().getFunctionListeners().get(i);
      listener.functionCreated(f.getUniqueID());
      }
    if (state != null)
      state.add(f);
    }

  public void removeFunction (String name, AssignmentState state) {
    // Framework
    state.remove((DomainObject) state.getData().getFunctionMap().get(name));
    state.getData().getFunctionMap().remove(name);
    }

  public String newChild (String containerID, short classID, String context, AssignmentState state) {
    DataObject codeBlock = null;
    if (classID == IVPConstants.MODEL_WHILE) {
      codeBlock = (CodeComposite) state.getData().getDataFactory().createWhile();
      initCodeBlock(containerID, codeBlock);
      createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_OPERATION_EQU, (short) -1, "while", state);
      }
    else if (classID == IVPConstants.MODEL_WRITE) {
      codeBlock = (DataObject) state.getData().getDataFactory().createPrint();
      initCodeBlock(containerID, codeBlock);
      createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "printable", state);
      }
    else if (classID == IVPConstants.MODEL_IFELSE) {
      codeBlock = (DataObject) state.getData().getDataFactory().createIfElse();
      initCodeBlock(containerID, codeBlock);
      createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_OPERATION_EQU, (short) -1, "ifElse", state);
      }
    else if (classID == IVPConstants.MODEL_ATTLINE) {
      codeBlock = (DataObject) state.getData().getDataFactory().createAttributionLine();
      initCodeBlock(containerID, codeBlock);
      createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "leftVar", state);
      }
    else if (classID == IVPConstants.MODEL_READ) {
      codeBlock = (DataObject) state.getData().getDataFactory().createRead();
      initCodeBlock(containerID, codeBlock);
      createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, (short) -1, "writable", state);
      }
    else if (classID == IVPConstants.MODEL_FOR) {
      codeBlock = (DataObject) state.getData().getDataFactory().createFor();
      initCodeBlock(containerID, codeBlock);
      createForExpressions((For) codeBlock, state);
      }
    CodeComposite container = (CodeComposite) Services.getModelMapping().get(containerID);
    if (container instanceof IfElse) {
      if (context.equals("if")) {
        container.addChild(codeBlock.getUniqueID());
        }
      else if (context.equals("else")) {
        ((IfElse) container).addElseChildT(codeBlock.getUniqueID());
        }
      }
    else {
      container.addChild(codeBlock.getUniqueID());
      }
    ICodeListener listener = (ICodeListener) state.getData().getCodeListeners().get(containerID);
    listener.addChild(codeBlock.getUniqueID(), context);
    // Framework
    state.add(codeBlock);
    return codeBlock.getUniqueID();
    }

  private void createForExpressions (For codeBlock, AssignmentState state) {
    Expression index = createExpression("", codeBlock.getUniqueID(), Expression.EXPRESSION_VARIABLE, Expression.EXPRESSION_INTEGER,
            state);
    updateExpressionListeners(codeBlock.getUniqueID(), Expression.EXPRESSION_INTEGER, "forIndex", state, index);
    putExpressionOnRightPlace(codeBlock.getUniqueID(), "forIndex", index);
    }

  private void initCodeBlock (String containerID, DataObject codeBlock) {
    codeBlock.setParentID(containerID);
    codeBlock.setScopeID(currentScope);
    Services.getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
    }

  public int moveChild (String component, String origin, String destiny, String originContext, String destinyContext, int dropIndex,
          AssignmentState _currentState) {
    CodeComposite destinyCode = (CodeComposite) Services.getModelMapping().get(destiny);
    CodeComposite originCode = (CodeComposite) Services.getModelMapping().get(origin);
    CodeComponent componentCode = (CodeComponent) Services.getModelMapping().get(component);
    int lastIndex = -1;
    ICodeListener destinyListener = (ICodeListener) Services.getViewMapping().get(destiny);
    ICodeListener originListener = (ICodeListener) Services.getViewMapping().get(origin);
    if (origin != destiny) {
      if (originCode instanceof IfElse) {
        if (originContext.equals("if")) {
          lastIndex = originCode.removeChild(component);
          } else if (originContext.equals("else")) {
          lastIndex = ((IfElse) originCode).removeElseChild(component);
          }
        }
      else {
        lastIndex = originCode.removeChild(component);
        }
      if (destinyCode instanceof IfElse) {
        if (destinyContext.equals("if")) {
          destinyCode.addChildToIndex(component, dropIndex);
          } else if (destinyContext.equals("else")) {
          ((IfElse) destinyCode).addElseChildToIndex(component, dropIndex);
          }
        }
      else {
        destinyCode.addChildToIndex(component, dropIndex);
        }
      originListener.childRemoved(component, originContext);
      destinyListener.restoreChild(component, dropIndex, destinyContext);
      }
    else {
      if (originCode instanceof IfElse) {
        if (originContext.equals("if")) {
          lastIndex = originCode.moveChild(component, dropIndex);
          } else if (originContext.equals("else")) {
          lastIndex = ((IfElse) originCode).moveElseChild(component, dropIndex);
          }
        }
      else {
        lastIndex = originCode.moveChild(component, dropIndex);
        }
      originListener.moveChild(component, originContext, dropIndex);
      }
    componentCode.setParentID(destiny);
    return lastIndex;
    }

  public int removeChild (String containerID, String childID, String context, AssignmentState state) {
    CodeComposite parent = (CodeComposite) Services.getModelMapping().get(containerID);
    int index = 0;
    if (parent instanceof IfElse) {
      if (context.equals("if")) {
        index = parent.removeChild(childID);
        }
      else if (context.equals("else")) {
        index = ((IfElse) parent).removeElseChild(childID);
        }
      }
    else {
      index = parent.removeChild(childID);
      }
    ICodeListener codeListener = (ICodeListener) state.getData().getCodeListeners().get(containerID);
    codeListener.childRemoved(childID, context);
    // Check child dependencies and remove them
    CodeComponent child = (CodeComponent) Services.getModelMapping().get(childID);
    if (child instanceof AttributionLine) {
      state.remove((DomainObject) Services.getModelMapping().get(((AttributionLine) child).getLeftVariableID()));
      }
    else if (child instanceof CodeComposite) {
      // remove children recursively
      }
    // Framework
    state.remove((DomainObject) Services.getModelMapping().get(childID));
    return index;
    }

  public void restoreChild (String containerID, String childID, int index, String context, AssignmentState state) {
    CodeComposite parent = (CodeComposite) Services.getModelMapping().get(containerID);
    if (parent instanceof IfElse) {
      if (context.equals("if")) {
        parent.addChildToIndex(childID, index);
        }
      else if (context.equals("else")) {
        ((IfElse) parent).addElseChildToIndex(childID, index);
        }
      }
    else {
      parent.addChildToIndex(childID, index);
      }
    ICodeListener codeListener = (ICodeListener) state.getData().getCodeListeners().get(containerID);
    codeListener.restoreChild(childID, index, context);
    }

  // Function actions
  public void createParameter (String scopeID) {
    }

  public void removeParameter (String scopeID, String parameterID) {
    }

  public String createVariable (String scopeID, String initialValue, AssignmentState state) {
    Function f = (Function) Services.getModelMapping().get(scopeID);
    Variable newVar = (Variable) state.getData().getDataFactory().createVariable();
    newVar.setVariableName("variavel" + f.getVariableCount());
    newVar.setVariableType(Expression.EXPRESSION_INTEGER);
    newVar.setVariableValue(initialValue);
    newVar.setScopeID(currentScope);
    Services.getModelMapping().put(newVar.getUniqueID(), newVar);
    f.addLocalVariable(newVar.getUniqueID());
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.addedVariable(newVar.getUniqueID());
      }
    state.add(newVar);
    return newVar.getUniqueID();
    }

  public void removeVariable (String scopeID, String variableID, AssignmentState state) {
    Function f = (Function) Services.getModelMapping().get(scopeID);
    f.removeLocalVariable(variableID);
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.removedVariable(variableID);
      }
    state.remove((DomainObject) Services.getModelMapping().get(variableID));
    }

  public void restoreVariable (String scopeID, String variableID, AssignmentState state) {
    Function f = (Function) Services.getModelMapping().get(scopeID);
    f.addLocalVariable(variableID);
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.variableRestored(variableID);
      }
    state.add((DomainObject) Services.getModelMapping().get(variableID));
    }

  public String updateReferencedVariable (String refID, String newVarRef, AssignmentState state) {
    String lastReferencedVariable = "";
    VariableReference ref = (VariableReference) Services.getModelMapping().get(refID);
    lastReferencedVariable = ref.getReferencedVariable();
    ref.setReferencedVariable(newVarRef);
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.updateReference(refID);
      }
    if (newVarRef != null && !"".equals(newVarRef)) {
      // significa que estou voltando ao estado inicial.
      Variable newReferenced = (Variable) Services.getModelMapping().get(newVarRef);
      newReferenced.addVariableReference(refID);
      }
    Variable lastReferenced = (Variable) Services.getModelMapping().get(lastReferencedVariable);
    if (lastReferenced != null) {
      lastReferenced.removeVariableReference(refID);
      }
    return lastReferencedVariable;
    }

  public String createExpression (String leftExpID, String holder, short expressionType, short primitiveType, String context, AssignmentState state) {
    Expression exp = createExpression(leftExpID, holder, expressionType, primitiveType, state);
    updateExpressionListeners(holder, expressionType, context, state, exp);
    putExpressionOnRightPlace(holder, context, exp);
    state.add(exp);
    return exp.getUniqueID();
    }

  private void updateExpressionListeners (String holder, short expressionType, String context, AssignmentState state, Expression exp) {
    for (int i = 0; i < state.getData().getExpressionListeners().size(); i++) {
      ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionCreated(holder, exp.getUniqueID(), context);
      }
    if (expressionType == Expression.EXPRESSION_OPERATION_AND || expressionType == Expression.EXPRESSION_OPERATION_OR) {
      Expression newExp = (Expression) state.getData().getDataFactory().createExpression();
      newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
      newExp.setParentID(exp.getUniqueID());
      newExp.setScopeID(currentScope);
      ((Operation) exp).setExpressionB(newExp.getUniqueID());
      Services.getModelMapping().put(newExp.getUniqueID(), newExp);
      for (int i = 0; i < state.getData().getExpressionListeners().size(); i++) {
        ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionCreated(exp.getUniqueID(), newExp.getUniqueID(), "right");
        }
      state.add(newExp);
      }
    }

  private void putExpressionOnRightPlace (String holder, String context, Expression exp) {
    if (context.equals("right")) {
      ((Operation) Services.getModelMapping().get(holder)).setExpressionB(exp.getUniqueID());
      }
    else if (context.equals("left")) {
      ((Operation) Services.getModelMapping().get(holder)).setExpressionA(exp.getUniqueID());
      }
    else if (context.equals("leftVar")) {
      ((AttributionLine) Services.getModelMapping().get(holder)).setLeftVariableID(exp.getUniqueID());
      }
    else if (context.equals("printable")) {
      ((Print) Services.getModelMapping().get(holder)).setPrintableObject(exp.getUniqueID());
      }
    else if (context.equals("writable")) {
      ((ReadData) Services.getModelMapping().get(holder)).setWritableObject(exp.getUniqueID());
      }
    else if (context.equals("while")) {
      ((While) Services.getModelMapping().get(holder)).setCondition(exp.getUniqueID());
      }
    else if (context.equals("ifElse")) {
      ((IfElse) Services.getModelMapping().get(holder)).setComparison(exp.getUniqueID());
      }
    else if (context.equals("forIndex")) {
      ((For) Services.getModelMapping().get(holder)).setIndexExpression(exp.getUniqueID());
      }
    else if (context.equals("forLowerBound")) {
      ((For) Services.getModelMapping().get(holder)).setLowerBoundExpression(exp.getUniqueID());
      }
    else if (context.equals("forUpperBound")) {
      ((For) Services.getModelMapping().get(holder)).setUpperBoundExpression(exp.getUniqueID());
      }
    else if (context.equals("forIncrement")) {
      ((For) Services.getModelMapping().get(holder)).setIncrementExpression(exp.getUniqueID());
      }
    }

  private Expression createExpression (String leftExpID, String holder, short expressionType, short primitiveType, AssignmentState state) {
    Expression exp = null;
    if (expressionType == Expression.EXPRESSION_VARIABLE) {
      exp = (Expression) state.getData().getDataFactory().createVarReference();
      exp.setExpressionType(Expression.EXPRESSION_VARIABLE);
      ((VariableReference) exp).setReferencedType(primitiveType);
      }
    else if (expressionType >= Expression.EXPRESSION_INTEGER && expressionType <= Expression.EXPRESSION_BOOLEAN) {
      exp = (Expression) state.getData().getDataFactory().createConstant();
      exp.setExpressionType(expressionType);
      ((Constant) exp).setConstantValue(getInitvalue(expressionType));
      }
    else {
      exp = (Expression) state.getData().getDataFactory().createExpression();
      exp.setExpressionType(expressionType);
      if (leftExpID != "") {
        ((Expression) Services.getModelMapping().get(leftExpID)).setParentID(exp.getUniqueID());
        ((Operation) exp).setExpressionA(leftExpID);
        }
      }
    exp.setParentID(holder);
    exp.setScopeID(currentScope);
    Services.getModelMapping().put(exp.getUniqueID(), exp);
    return exp;
    }


  public String deleteExpression (String expression, String holder, String context, boolean isClean, boolean isComparison, AssignmentState state) {
    Expression exp = (Expression) Services.getModelMapping().get(expression);
    DataObject dataHolder = (DataObject) Services.getModelMapping().get(holder);
    String lastExpressionID;
    if (dataHolder instanceof AttributionLine) {
      ((AttributionLine) dataHolder).setRightExpression("");
      lastExpressionID = "";
      }
    else if (dataHolder instanceof Print) { //TODO it seems that the 'Print' is not more been used to remove!!!
      lastExpressionID = "";
      }
    else if (dataHolder instanceof Operation) {
      ((Operation) dataHolder).removeExpression(expression);
      lastExpressionID = ((Operation) dataHolder).getExpressionA();
      }
    else if (dataHolder instanceof For) {
      ((For) dataHolder).removeExpression(expression, context);
      }
    for (int i = 0; i < state.getData().getExpressionListeners().size(); i++) {
      ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionDeleted(expression, context, isClean);
      }
    // checar aqui...
    if (isClean) {
      Expression newExp;
      // TODO: Hora de remover tudo e reinicializar a porra toda...
      if (!isComparison) {
        newExp = (Expression) state.getData().getDataFactory().createVarReference();
        newExp.setExpressionType(Expression.EXPRESSION_VARIABLE);
        if (dataHolder instanceof For) {
          ((VariableReference) newExp).setReferencedType(Expression.EXPRESSION_INTEGER);
          }
	else if (dataHolder instanceof AttributionLine) {
          ((VariableReference) newExp).setReferencedType(((AttributionLine) dataHolder).getLeftVariableType());
          }
        else if (dataHolder instanceof Operation) {
          short theOperationType = ((Operation) dataHolder).getExpressionType();
          ((VariableReference) newExp).setReferencedType(theOperationType);
          }
        }
      else {
        newExp = (Expression) state.getData().getDataFactory().createExpression();
        newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
        }
      newExp.setParentID(holder);
      newExp.setScopeID(currentScope);
      Services.getModelMapping().put(newExp.getUniqueID(), newExp);
      for (int i = 0; i < state.getData().getExpressionListeners().size(); i++) {
        ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionCreated(holder, newExp.getUniqueID(),
                context);
        }
      if (state != null)
        state.add(newExp);
      }
    if (state != null)
      state.remove(exp);
    return expression;
    }

  public void restoreExpression (String expression, String holder, String context, boolean wasClean, AssignmentState state) {
    Expression exp = (Expression) Services.getModelMapping().get(expression);
    for (int i = 0; i < state.getData().getExpressionListeners().size(); i++) {
      if (wasClean) {
        ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionRestoredFromCleaning(holder, expression, context);
        }
      else {
        ((IExpressionListener) state.getData().getExpressionListeners().get(i)).expressionRestored(holder, expression, context);
        }
      }
    state.add(exp);
    }

  public short changeOperationSign (String expression, String context, short newType, AssignmentState state) {
    Expression exp = (Expression) Services.getModelMapping().get(expression);
    short lastType = exp.getExpressionType();
    exp.setExpressionType(newType);
    for (int i = 0; i < state.getData().getOperationListeners().size(); i++) {
      ((IOperationListener) state.getData().getOperationListeners().get(i)).operationTypeChanged(expression, context);
      }
    return lastType;
    }

  public String changeValue (String id, String constantValue, AssignmentState state) {
    Constant c = (Constant) Services.getModelMapping().get(id);
    String lastValue = c.getConstantValue();
    c.setConstantValue(constantValue);
    IValueListener v = (IValueListener) Services.getViewMapping().get(id);
    v.valueChanged(constantValue);
    return lastValue;
    }

  public String changeVariableName (String id, String name, AssignmentState state) {
    Variable v = (Variable) Services.getModelMapping().get(id);
    String lastName = v.getVariableName();
    v.setVariableName(name);
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.changeVariableName(id, name, lastName);
      }
    for (int i = 0; i < v.getVariableReferenceList().size(); i++) {
      Reference r = (Reference) Services.getModelMapping().get(v.getVariableReferenceList().get(i));
      r.setReferencedName(v.getVariableName());
      }
    state.updateState((DomainObject) Services.getModelMapping().get(id));
    return lastName;
    }

  public Vector changeVariableType (String id, short newType, AssignmentState state) {
    Variable v = (Variable) Services.getModelMapping().get(id);
    for (int i = 0; i < v.getVariableReferenceList().size(); i++) {
      Reference r = (Reference) Services.getModelMapping().get(v.getVariableReferenceList().get(i));
      r.setReferencedType(newType);
      }
    short lastType = v.getVariableType();
    Vector returnedVector = new Vector();
    returnedVector.add(lastType);
    v.setVariableType(newType);
    v.setVariableValue(getInitvalue(newType));
    Vector attLines = new Vector();
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.changeVariableType(id, newType);
      if (listener instanceof VariableSelectorUI) {
        if (((VariableSelectorUI) listener).isIsolated()) {
          String modelParentID = ((VariableSelectorUI) listener).getModelParent();
          if (Services.getModelMapping().get(modelParentID) instanceof AttributionLine) {
            attLines.add(modelParentID);
            }
          }
        }
      }
    for (int i = 0; i < attLines.size(); i++) { // ta errado... s� posso
                          // mexer na attLine se eu
                          // estiver mostrando (na ref
                          // da esquerda) a var que
                          // mudou
      AttributionLine attLine = (AttributionLine) Services.getModelMapping().get(attLines.get(i));
      VariableReference varRef = (VariableReference) Services.getModelMapping().get(attLine.getLeftVariableID());
      if (attLine.getLeftVariableType() != newType && id.equals(varRef.getReferencedVariable())) {
        state.remove((DomainObject) Services.getModelMapping().get(attLine.getRightExpressionID()));
        attLine.setLeftVariableType(newType);
        AttributionLineUI attLineUI = (AttributionLineUI) Services.getViewMapping().get(attLines.get(i));
        attLineUI.updateHoldingType(newType);
        returnedVector.add(deleteExpression(attLine.getRightExpressionID(), attLine.getUniqueID(), "", true, false, state));
        }
      }
    state.updateState(v);
    return returnedVector;
    }

  public void restoreVariableType (String id, Vector ret, AssignmentState state) {
    Variable v = (Variable) Services.getModelMapping().get(id);
    v.setVariableType((Short) ret.get(0));
    v.setVariableValue(getInitvalue((Short) ret.get(0)));
    Vector attLines = new Vector();
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.changeVariableType(id, (Short) ret.get(0));
      if (listener instanceof VariableSelectorUI) {
        if (((VariableSelectorUI) listener).isIsolated()) {
          String modelParentID = ((VariableSelectorUI) listener).getModelParent();
          if (Services.getModelMapping().get(modelParentID) instanceof AttributionLine) {
            attLines.add(modelParentID);
            }
          }
        }
      }
    for (int i = 0; i < attLines.size(); i++) {
      AttributionLine attLine = (AttributionLine) Services.getModelMapping().get(attLines.get(i));
      VariableReference varRef = (VariableReference) Services.getModelMapping().get(attLine.getLeftVariableID());
      if (attLine.getLeftVariableType() != (Short) ret.get(0) && id.equals(varRef.getReferencedVariable())) {
        attLine.setLeftVariableType((Short) ret.get(0));
        AttributionLineUI attLineUI = (AttributionLineUI) Services.getViewMapping().get(attLines.get(i));
        attLineUI.updateHoldingType((Short) ret.get(0));
        }
      }
    for (int i = 1; i < ret.size(); i++) {
      String restoredID = (String) ret.get(i);
      String holderID = ((Expression) Services.getModelMapping().get(ret.get(i))).getParentID();
      restoreExpression(restoredID, holderID, "", true, state);
      state.add((DomainObject) Services.getModelMapping().get((String) ret.get(i)));
      }
    state.updateState(v);
    }

  public String changeVariableInitialValue (String id, String value, AssignmentState state) {
    Variable v = (Variable) Services.getModelMapping().get(id);
    String lastValue = v.getVariableValue();
    v.setVariableValue(value);
    for (int i = 0; i < state.getData().getVariableListeners().size(); i++) {
      IVariableListener listener = (IVariableListener) state.getData().getVariableListeners().get(i);
      listener.changeVariableValue(id, value);
      }
    state.updateState((DomainObject) Services.getModelMapping().get(id));
    return lastValue;
    }

  public int changeForMode (int newMode, String forID, AssignmentState state) {
    For f = (For) Services.getModelMapping().get(forID);
    int lastMode = f.getCurrentForMode();
    f.setCurrentForMode(newMode);
    return lastMode;
    }

  public AssignmentState getNewAssignmentState () {
    return new AssignmentState();
    }

  public void setConsoleListener (IVPConsole ivpConsoleUI) {
    try {
      interpreter.set("ivpConsole", ivpConsoleUI);
      } catch (EvalError e) {
      e.printStackTrace();
      }
    console = ivpConsoleUI;
    }

  public void updateParent (String parentModelID, String currentModelID, String newExp, String operationContext) {
    DataObject parent = (DataObject) Services.getModelMapping().get(parentModelID);
    if (parent instanceof CodeComponent) {
      ((CodeComponent) parent).updateParent(currentModelID, newExp, operationContext);
      }
    else if (parent instanceof Operation) {
      ((Operation) parent).updateParent(currentModelID, newExp, operationContext);
      }
    }

  public short updateAttLineType (String attLineID, short newType) {
    AttributionLine attLine = (AttributionLine) Services.getModelMapping().get(attLineID);
    short lastType = attLine.getLeftVariableType();
    attLine.setLeftVariableType(newType);
    deleteExpression(attLine.getRightExpressionID(), attLineID, "", true, false, null);
    ((AttributionLineUI) Services.getViewMapping().get(attLineID)).updateHoldingType(newType);
    return lastType;
    }

  public String getInitvalue (short type) {
    if (type == Expression.EXPRESSION_BOOLEAN) {
      return "true";
      }
    else if (type == Expression.EXPRESSION_DOUBLE) {
      return "1.0";
      }
    else if (type == Expression.EXPRESSION_INTEGER) {
      return "1";
      }
    else if (type == Expression.EXPRESSION_STRING) {
      return ResourceBundleIVP.getString("helloWorld.text");
      }
    return "";
    }

  public void addComponentListener (ICodeListener listener, String id) {
    Services.getCurrentState().getData().getCodeListeners().put(id, listener);
    }

  public HashMap getCodeListener () {
    return Services.getCurrentState().getData().getCodeListeners();
    }

  public HashMap getFunctionMap () {
    return Services.getCurrentState().getData().getFunctionMap();
    }

  public void setFunctionMap (HashMap functionMap) {
    Services.getCurrentState().getData().setFunctionMap(functionMap);
    }

  public void addExpressionListener (IExpressionListener listener) {
    if (!Services.getCurrentState().getData().getExpressionListeners().contains(listener)) {
      Services.getCurrentState().getData().getExpressionListeners().add(listener);
      }
    }

  public void addOperationListener (IOperationListener listener) {
    if (!Services.getCurrentState().getData().getOperationListeners().contains(listener)) {
      Services.getCurrentState().getData().getOperationListeners().add(listener);
      }
    }

  public void addVariableListener (IVariableListener listener) {
    Services.getCurrentState().getData().getVariableListeners().add(listener);
    }

  public void addFunctionListener (IFunctionListener listener) {
    Services.getCurrentState().getData().getFunctionListeners().add(listener);
    }

  boolean error = false;

  public void playCode () {
    if (Services.getController().isContentSet()) {
      Services.getController().lockCodeDown();
      String code = "";
      Object[] functionList = Services.getCurrentState().getData().getFunctionMap().values().toArray();
      for (int i = 0; i < functionList.length; i++) {
        code += " " + ((Function) functionList[i]).toJavaString() + " ";
        }
      code += " Principal(); ";
      if (error) {
        console.clean();
        error = false;
        }
      try {
        interpreter.set("isEvaluating", false);
        interpreter.eval(code);
      } catch (EvalError e) {
        if (e.getCause() != null) {
          if (e.getCause().equals("/ by zero")) {
            console.printError(ResourceBundleIVP.getString("IVPProgram.DivByZeroMessage"));
            }
          }
        e.printStackTrace();
        }
      }
    else {
      error = true;
      printError(ResourceBundleIVP.getString("Error.fieldsNotSet"));
      }
    }

  public void printError (String errorMessage) {
    console.printError(errorMessage);
    }

  public boolean validateVariableName (String modelScopeID, String value) {
    // TODO: generalize. each scope has to have a single localVarMap. It's
    // easier to check for each scope;
    Function f = (Function) Services.getModelMapping().get(modelScopeID);
    Vector v = f.getLocalVariableMap().toVector();
    for (int i = 0; i < v.size(); i++) {
      Variable var = (Variable) Services.getModelMapping().get(v.get(i));
      if (var.getVariableName().equals(value))
        return false;
      }
    return true;
    }

  public float AutomaticChecking (AssignmentState studentAnswer, AssignmentState expectedAnswer) {
    return 0;
    }

  public float getEvaluation (String tests) {
    if (Services.getController().isContentSet()) {
      Services.getController().lockCodeDown();
      Stack input = new Stack();
      Stack output = new Stack();
      Stack interpreterOutput = new Stack();
      int nTests = 0;
      nTests = prepareInputAndOutput(tests, input, output);
      Stack invertedInput = new Stack();
      int size = input.size();
      for (int i = 0; i < size; i++) {
        invertedInput.push(input.pop());
        }
      input = invertedInput;
      int contador = 0;
      while (contador < nTests) {
        if (Services.getController().isContentSet()) {
          Services.getController().lockCodeDown();
          String code = "";
          Object[] functionList = Services.getCurrentState().getData().getFunctionMap().values().toArray();
          for (int i = 0; i < functionList.length; i++) {
            code += " " + ((Function) functionList[i]).toJavaString() + " ";
            }
          code += " Principal(); ";
          if (error) {
            console.clean();
            error = false;
            }
          try {
            interpreter.set("isEvaluating", true);
            interpreter.set("interpreterOutput", interpreterOutput);
            interpreter.set("interpreterInput", input);
            interpreter.eval(code);
            } catch (EvalError e) {
            if (e.getCause() != null) {
              if (e.getCause().equals("/ by zero")) {
                console.printError(ResourceBundleIVP.getString("IVPProgram.DivByZeroMessage"));
                }
              }
            e.printStackTrace();
            }
          }
        else {
          error = true;
          printError(ResourceBundleIVP.getString("Error.fieldsNotSet"));
          }
        contador++;
        }
      int match = 0;
      for (int i = 0; i < nTests; i++) {
        Object o1 = null, o2 = null;
        if (!interpreterOutput.isEmpty()) {
          o1 = interpreterOutput.pop();
          }
        if (!output.isEmpty()) {
          o2 = output.pop();
          }
        if (o1 != null && o2 != null) {
          String o1Str = "";
          String o2Str = "";
          if (o1 instanceof Double) {
            o1Str = String.format("%.4f", o1);
            }
          else {
            o1Str = new String(o1 + "");
            }
          if (o1 instanceof Double) {
            o2Str = String.format("%.4f", o2);
            }
          else {
            o2Str = new String(o2 + "");
            }
          if (o1Str.equals(o2Str)) {
            match++;
            }
          }
        }
      float resultado = 0;
      if (match != 0) {
        resultado = (float) (match * 1.0 / nTests);
        float porcentagem = resultado * 100;
        String number = String.format("%.2f", porcentagem);
        console.clean();
        console.println("-------------------------------------------------");
        console.println("Total de testes: " + nTests);
        console.println("Passou em " + match + " testes.");
        console.println("Aproveitamento: " + number + "%.");
        console.println("-------------------------------------------------");
        return resultado;
        }
      else {
        resultado = (float) (match * 1.0 / nTests);
        float porcentagem = resultado * 100;
        String number = String.format("%.2f", porcentagem);
        console.clean();
        console.println("-------------------------------------------------");
        console.println("Total de testes: " + nTests);
        console.println("Passou em " + match + " testes.");
        console.println("Aproveitamento: 0%.");
        console.println("-------------------------------------------------");
        return 0;
        }
      }
    return 0;
    }

  private int prepareInputAndOutput (String tests, Stack input, Stack outputStack) {
    Document doc = null;
    int testCount = 0;
    try {
      doc = loadXMLFromString(tests);
    } catch (Exception e) {
      e.printStackTrace();
      }
    Element node = doc.getDocumentElement();
    NodeList nodeList = node.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node currentNode = nodeList.item(i);
      if ("testcase".equals(currentNode.getNodeName())) {
        testCount++;
        }
      String value = currentNode.getNodeName().trim();
      NodeList nodes = currentNode.getChildNodes();
      HashMap parameters = new HashMap();
      if (!value.equals("#text")) {
        for (int j = 0; j < nodes.getLength(); j++) {
          Node cnode = nodes.item(j);
          if (!cnode.getNodeName().equals("#text")) {
            if (cnode.getNodeName().equals("input")) {
              if (cnode.hasAttributes()) {
                input.push(parseValue(cnode.getAttributes().getNamedItem("type").getTextContent(), cnode.getTextContent()));
                }
              }
            else if (cnode.getNodeName().equals("output")) {
              if (cnode.hasAttributes()) {
                outputStack.push(parseValue(cnode.getAttributes().getNamedItem("type").getTextContent(), cnode.getTextContent()));
                }
              }
            }
          }
        }
      }
    return testCount;
    }

  private Object parseValue (String type, String value) {
    if (type.equals("int")) {
      return ((Integer) new Integer(value)).intValue();
      }
    else if (type.equals("double")) {
      return ((Double) new Double(value)).doubleValue();
      }
    else if (type.equals("String")) {
      return new String(value);
      }
    else if (type.equals("boolean")) {
      return ((Boolean) new Boolean(value)).booleanValue();
      }
    else if (type.equals("float")) {
      return ((Float) new Float(value)).floatValue();
      }
    return null;
    }

  public static Document loadXMLFromString (String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

  }
