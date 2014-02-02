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

	private HashMap globalVariables = null;
	private HashMap preDefinedFunctions = null;
	private HashMap functionMap = null;
	private DataFactory dataFactory = null;
	private List variableListeners;
	private List functionListeners;
	private List expressionListeners;
	private List operationListeners;
	private ConsoleInterface consoleListener;
	private Interpreter interpreter;
	
	private String currentScope = "0";

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
	public void createFunction(String name, String functionType, AssignmentState state) {
		Function f = (Function) dataFactory.createFunction();
		f.setFunctionName(name);
		f.setReturnType(functionType);
		functionMap.put(name, f);
		Services.getService().getModelMapping().put(f.getUniqueID(), f);
		for(int i = 0; i < functionListeners.size(); i++){
			IFunctionListener listener = (IFunctionListener) functionListeners.get(i);
			listener.functionCreated(f.getUniqueID());
		}
		if(state!=null) state.add(f);
	}

	public void removeFunction(String name, AssignmentState state) {
		//Framework
		state.remove((DomainObject) functionMap.get(name));
		functionMap.remove(name);
	}

	public String newChild(String containerID, short classID, AssignmentState state) {
		DataObject codeBlock = null;
		if(classID == IVPConstants.MODEL_WHILE){
			codeBlock = (CodeComposite) dataFactory.createWhile();
			Operation op = (Operation) dataFactory.createExpression();
			op.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
			op.setScopeID(currentScope);
			op.setParentID(codeBlock.getUniqueID());
			((While)codeBlock).setCondition(op.getUniqueID());
			Services.getService().getModelMapping().put(op.getUniqueID(), op);
			state.add(op);
		} else if(classID == IVPConstants.MODEL_WRITE){
			codeBlock = (DataObject) dataFactory.createPrint();
			VariableReference e = (VariableReference) dataFactory.createVarReference();
			e.setScopeID(currentScope);
			e.setParentID(codeBlock.getUniqueID());
			e.setExpressionType(Expression.EXPRESSION_VARIABLE);
			System.out.println("no modelo a expressão é "+e.getUniqueID());
			Services.getService().getModelMapping().put(e.getUniqueID(), e);
			state.add(e);
			((Print)codeBlock).setPrintableObject(e.getUniqueID());
		} else if( classID == IVPConstants.MODEL_ATTLINE){
			codeBlock = (DataObject) dataFactory.createAttributionLine();
			VariableReference varRef = (VariableReference) dataFactory.createVarReference();
			varRef.setScopeID(currentScope);
			varRef.setParentID(codeBlock.getUniqueID());
			varRef.setExpressionType(Expression.EXPRESSION_VARIABLE);
			((AttributionLine)codeBlock).setLeftVariableID(varRef.getUniqueID());
			Services.getService().getModelMapping().put(varRef.getUniqueID(), varRef);
			state.add(varRef);
		}
		Services.getService().getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
		CodeComposite container = (CodeComposite) Services.getService().getModelMapping().get(containerID);
		codeBlock.setParentID(containerID);
		codeBlock.setScopeID(currentScope);
		container.addChild(codeBlock.getUniqueID());
		//Framework
		state.add(codeBlock);
		return codeBlock.getUniqueID();
	}
	
	public void playCode(){
		String code = " ";
		Object[] functionList = functionMap.values().toArray();
		for(int i = 0; i < functionList.length; i++){
			code += " "+((Function)functionList[i]).toJavaString()+" ";
		}
		code += " Principal(); ";
		
		System.out.println(code);

		try {
			interpreter.eval(code);
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}

	public String updateReferencedVariable(String refID, String newVarRef, AssignmentState state){
		String lastReferencedVariable = "";
		VariableReference ref = (VariableReference) Services.getService().getModelMapping().get(refID);
		lastReferencedVariable = ref.getReferencedVariable();
		ref.setReferencedVariable(newVarRef);
		for(int i = 0; i < variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.updateReference(refID);
		}
		Variable newReferenced = (Variable) Services.getService().getModelMapping().get(newVarRef);
		newReferenced.addVariableReference(refID);
		Variable lastReferenced = (Variable) Services.getService().getModelMapping().get(lastReferencedVariable);
		if(lastReferenced != null && !"".equals(lastReferenced)) { 
			lastReferenced.removeVariableReference(refID); 
		}
		return lastReferencedVariable;
	}
	
	public int removeChild(String containerID, String childID, AssignmentState state) {
		CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
		int index = 0;
		index = parent.removeChild(childID);
		ICodeListener codeListener = (ICodeListener) Services.getService().getController().getCodeListener().get(containerID);
		codeListener.childRemoved(childID);
		//Check child dependencies and remove them
		CodeComponent child = (CodeComponent) Services.getService().getModelMapping().get(childID);
		if(child instanceof AttributionLine){
			state.remove((DomainObject) Services.getService().getModelMapping().get(((AttributionLine)child).getLeftVariableID()));
		}else if(child instanceof CodeComposite){
			//remove children recursively
		}
		//Framework
		state.remove((DomainObject) Services.getService().getModelMapping().get(childID));
		return index;
	}
	
	public void restoreChild(String containerID, String childID, int index, AssignmentState state){
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

	public String createVariable(String scopeID,  String initialValue, AssignmentState state) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		Variable newVar = (Variable) dataFactory.createVariable();
		newVar.setVariableName("newVar" + f.getVariableCount());
		newVar.setVariableType(Variable.TYPE_INTEGER);
		newVar.setVariableValue(initialValue);
		newVar.setScopeID(currentScope);
		Services.getService().getModelMapping().put(newVar.getUniqueID(), newVar);
		f.addLocalVariable(newVar.getUniqueID());
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.addedVariable(newVar.getUniqueID());
		}
		state.add(newVar);
		return newVar.getUniqueID();
	}

	public void removeVariable(String scopeID, String variableID, AssignmentState state) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.removeLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.removedVariable(variableID);
		}
		state.remove((DomainObject) Services.getService().getModelMapping().get(variableID));
	}
	
	public void restoreVariable(String scopeID, String variableID, AssignmentState state){
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.addLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.variableRestored(variableID);
		}
		state.add((DomainObject) Services.getService().getModelMapping().get(variableID));
	}
	
	public String createExpression(String leftExpID, String holder, short expressionType, String context, AssignmentState state){
		// new expression ->    (leftExp SIGN newExpField)
		Expression exp = null;
		if(expressionType == Expression.EXPRESSION_VARIABLE){
			exp = (Expression) dataFactory.createVarReference();
			exp.setExpressionType(expressionType);
			
		} else {
			exp = (Expression) dataFactory.createExpression();
			exp.setExpressionType(expressionType);
			((Expression)Services.getService().getModelMapping().get(leftExpID)).setParentID(exp.getUniqueID());
			((Operation) exp).setExpressionA(leftExpID);
		}
		exp.setParentID(holder);
		exp.setScopeID(currentScope);
		Services.getService().getModelMapping().put(exp.getUniqueID(), exp);
		for(int i = 0; i < expressionListeners.size(); i++){
			((IExpressionListener)expressionListeners.get(i)).expressionCreated(holder, exp.getUniqueID(), context);
		}
		if(expressionType == Expression.EXPRESSION_OPERATION_AND || expressionType == Expression.EXPRESSION_OPERATION_OR){
			Expression newExp = (Expression) dataFactory.createExpression();
			newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
			newExp.setParentID(exp.getUniqueID());
			newExp.setScopeID(currentScope);
			((Operation)exp).setExpressionB(newExp.getUniqueID());
			Services.getService().getModelMapping().put(newExp.getUniqueID(), newExp);
			for(int i = 0; i < expressionListeners.size(); i++){
				((IExpressionListener)expressionListeners.get(i)).expressionCreated(exp.getUniqueID(), newExp.getUniqueID(), "right");
			}
			state.add(newExp);
		}
		state.add(exp);
		return exp.getUniqueID();
	}
	
	public String deleteExpression(String expression, String holder, String context, boolean isClean, boolean isComparison, AssignmentState state) {
		Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
		DataObject dataHolder = (DataObject)Services.getService().getModelMapping().get(holder);
		String lastExpressionID;
		if(dataHolder instanceof AttributionLine){
			((AttributionLine)dataHolder).setRightExpression(null);
			lastExpressionID = null;
		} else if(dataHolder instanceof Print){
			lastExpressionID = null;
		} else if(dataHolder instanceof Operation){
			((Operation)dataHolder).removeExpression(expression);
			lastExpressionID = ((Operation)dataHolder).getExpressionA();
		}
		for(int i = 0; i < expressionListeners.size(); i++){
			((IExpressionListener)expressionListeners.get(i)).expressionDeleted(expression, context, isClean);
		}
		if(isClean){
			Expression newExp;
			if(!isComparison){
				newExp = (Expression) dataFactory.createVarReference();
				newExp.setExpressionType(Expression.EXPRESSION_VARIABLE);
			}else{
				newExp = (Expression) dataFactory.createExpression();
				newExp.setExpressionType(Expression.EXPRESSION_OPERATION_EQU);
			}
			newExp.setParentID(holder);
			newExp.setScopeID(currentScope);
			Services.getService().getModelMapping().put(newExp.getUniqueID(), newExp);
			for(int i = 0; i < expressionListeners.size(); i++){
				((IExpressionListener)expressionListeners.get(i)).expressionCreated(holder, newExp.getUniqueID(), context);
			}
			state.add(newExp);
		}
		state.remove(exp);
		return expression;
	}
	
	public void restoreExpression(String expression, String holder, String context, boolean wasClean, AssignmentState state) {
		Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
		for(int i = 0; i < expressionListeners.size(); i++){
			if(wasClean){
				((IExpressionListener)expressionListeners.get(i)).expressionRestoredFromCleaning(holder, exp.getUniqueID(), context);
			}else{
				((IExpressionListener)expressionListeners.get(i)).expressionRestored(holder, exp.getUniqueID(), context);	
			}
			
		}
		state.add(exp);
	}
	
	public short changeOperationSign(String expression, String context, short newType, AssignmentState state){
		Expression exp = (Expression) Services.getService().getModelMapping().get(expression);
		short lastType = exp.getExpressionType();
		exp.setExpressionType(newType);
		for(int i = 0; i < operationListeners.size(); i++){
			((IOperationListener)operationListeners.get(i)).operationTypeChanged(expression, context);
		}
		return lastType;
	}
	
	public void addExpressionListener(IExpressionListener listener){
		if(!expressionListeners.contains(listener)){
			expressionListeners.add(listener);
		}
	}
	
	public void addOperationListener(IOperationListener listener){
		if(!operationListeners.contains(listener)){
			operationListeners.add(listener);
		}
	}

	public void addVariableListener(IVariableListener listener){
		variableListeners.add(listener);
	}
	
	public void addFunctionListener(IFunctionListener listener){
		functionListeners.add(listener);
	}
	
	public String changeVariableName(String id, String name, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		String lastName = v.getVariableName();
		v.setVariableName(name);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableName(id, name, lastName);
		}
		for(int i = 0; i < v.getVariableReferenceList().size(); i++){
			Reference r = (Reference) Services.getService().getModelMapping().get(v.getVariableReferenceList().get(i));
			r.setReferencedName(v.getVariableName());
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
		return lastName;
	}
	
	public String changeVariableType(String id, String type, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		String lastType = v.getVariableType();
		v.setVariableType(type);
		for(int i = 0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableType(id, type);
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
		return lastType;
	}
	
	public void changeVariableInitialValue(String id, String value, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		v.setVariableValue(value);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableValue(id, value);
		}
		for(int i = 0; i < v.getVariableReferenceList().size(); i++){
			Reference ref = (Reference) v.getVariableReferenceList().get(i);
			ref.setReferencedName(v.getVariableName());
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
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

}
