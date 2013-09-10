package usp.ime.line.ivprog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.utils.IVPMapping;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends Observable {

	private HashMap globalVariables = null;
	private HashMap preDefinedFunctions = null;
	private HashMap createdFunctions = null;
	private DataFactory dataFactory = null;
	private int varCount = 0;
	
	private List variableListeners;

	public IVPProgram() {
		globalVariables = new HashMap();
		preDefinedFunctions = new HashMap();
		createdFunctions = new HashMap();
		dataFactory = new DataFactory();
		variableListeners = new Vector();
	}

	public void initializeModel() {
		createFunction(ResourceBundleIVP.getString("mainFunctionName"),
				ModelConstants.FUNC_RETURN_VOID);
	}

	// Program actions
	public void createFunction(String name, short functionType) {
		Function f = (Function) dataFactory.createFunction();
		f.setFunctionName(name);
		f.setReturnType(functionType);
		createdFunctions.put(name, f);
		notifyCodeCompositeCreated(f);
	}

	public void removeFunction(String name) {
		createdFunctions.remove(name);
	}

	// Composite actions
	public void addChild(CodeComposite target, CodeComponent child, int index) {
		//target.addChildToIndex(child, index);
	}

	public void removeChild(CodeComposite target, int index) {
		target.removeChildFromIndex(index);
	}

	// Function actions
	public void createParameter(String name, short type) {

	}

	public void removeParameter(String name) {

	}

	public void createVariable(Function f) {
		Variable newVar = (Variable) dataFactory.createVariable();
		newVar.setVariableName("newVar" + f.getVariableID());
		newVar.setVariableType(ModelConstants.VAR_INT_TYPE);
		newVar.setEscopeID(f.getUniqueID());
		
		Services.getService().getModelMapping().put(newVar.getUniqueID(), newVar);
		
		f.addLocalVariable(newVar.getUniqueID());
		
		
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.addedVariable(newVar.getUniqueID());
		}
	}

	public void removeVariable(String scopeID, String varID) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		Variable v = (Variable) Services.getService().getModelMapping().get(varID);
		f.removeLocalVariable(varID);
		
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.removedVariable(v.getUniqueID());
		}
	}

	private void notifyCodeCompositeCreated(DataObject dataObject) {
		Services.getService().getModelMapping().put(dataObject.getUniqueID(), dataObject);
		setChanged();
		notifyObservers(dataObject.getUniqueID());
	}
	
	public void addVariableListener(IVariableListener listener){
		variableListeners.add(listener);
	}
	public void changeVariableName(String id, String name){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		v.setVariableName(name);
		
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableName(id, name);
		}
	}

}
