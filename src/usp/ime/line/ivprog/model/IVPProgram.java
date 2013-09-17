package usp.ime.line.ivprog.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends DomainModel {

	private HashMap globalVariables = null;
	private HashMap preDefinedFunctions = null;
	private HashMap functionMap = null;
	private DataFactory dataFactory = null;
	private List variableListeners;
	private List functionListeners;

	public IVPProgram() {
		globalVariables = new HashMap();
		preDefinedFunctions = new HashMap();
		functionMap = new HashMap();
		dataFactory = new DataFactory();
		variableListeners = new Vector();
		functionListeners = new Vector();
	}

	public void initializeModel() {
		createFunction(ResourceBundleIVP.getString("mainFunctionName"), IVPConstants.FUNC_RETURN_VOID);
	}

	// Program actions
	public void createFunction(String name, short functionType) {
		Function f = (Function) dataFactory.createFunction();
		f.setFunctionName(name);
		f.setReturnType(functionType);
		functionMap.put(name, f);
		Services.getService().getModelMapping().put(f.getUniqueID(), f);
		for(int i = 0; i < functionListeners.size(); i++){
			IFunctionListener listener = (IFunctionListener) functionListeners.get(i);
			listener.functionCreated(f.getUniqueID());
		}
	}

	public void removeFunction(String name) {
		functionMap.remove(name);
	}

	public String newChild(String containerID, short classID) {
		
		
		CodeComposite codeBlock = null;
		if(classID == IVPConstants.MODEL_WHILE){
			codeBlock = (CodeComposite) dataFactory.createWhile();
		}
		Services.getService().getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
		CodeComposite container = (CodeComposite) Services.getService().getModelMapping().get(containerID);
		container.addChild(codeBlock.getUniqueID());
		return codeBlock.getUniqueID();
	}

	public void removeChild() {
	}

	// Function actions
	public void createParameter(String scopeID) {

	}

	public void removeParameter(String scopeID, String parameterID) {

	}

	public String createVariable(String scopeID) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		Variable newVar = (Variable) dataFactory.createVariable();
		newVar.setVariableName("newVar" + f.getVariableCount());
		newVar.setVariableType(IVPConstants.VAR_INT_TYPE);
		newVar.setEscopeID(f.getUniqueID());
		Services.getService().getModelMapping().put(newVar.getUniqueID(), newVar);
		f.addLocalVariable(newVar.getUniqueID());
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.addedVariable(newVar.getUniqueID());
		}
		return newVar.getUniqueID();
	}

	public void removeVariable(String scopeID, String variableID) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.removeLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.removedVariable(variableID);
		}
	}
	
	public void restoreVariable(String scopeID, String variableID){
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.addLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.addedVariable(variableID);
		}
	}

	public void addVariableListener(IVariableListener listener){
		variableListeners.add(listener);
	}
	
	public void addFunctionListener(IFunctionListener listener){
		functionListeners.add(listener);
	}
	
	public String changeVariableName(String id, String name){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		String lastName = v.getVariableName();
		v.setVariableName(name);
		System.out.println("Chegou aqui.");
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableName(id, name);
		}
		return lastName;
	}
	
	public void changeVariableInitialValue(String id, String value){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		v.setVariableValue(value);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableValue(id, value);
		}
	}

	public AssignmentState getNewAssignmentState() {
		return new AssignmentState();
	}

	public float AutomaticChecking(AssignmentState studentAnswer, AssignmentState expectedAnswer) {
		return 0;
	}

}
