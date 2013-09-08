package usp.ime.line.ivprog.model;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import usp.ime.line.ivprog.controller.IVPMapping;
import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends Observable {

	private HashMap globalVariables = null;
	private HashMap preDefinedFunctions = null;
	private HashMap createdFunctions = null;
	private DataFactory dataFactory = null;
	private int varCount = 0;
	
	public IVPProgram(){
		globalVariables = new HashMap();
		preDefinedFunctions = new HashMap();
		createdFunctions = new HashMap();
		dataFactory = new DataFactory();
	}
	
	public void initializeModel(){
		createFunction(ResourceBundleIVP.getString("mainFunctionName"), ModelConstants.FUNC_RETURN_VOID);
	}
	
	//Program actions
	public void createFunction(String name, short functionType){
		Function f = (Function) dataFactory.createFunction();
		f.setFunctionName(name);
		f.setReturnType(functionType);
		createdFunctions.put(name, f);
		notifyCodeCompositeCreated(f);
	}
	
	public void removeFunction(String name){
		createdFunctions.remove(name);
	}
	
	//Composite actions
	public void addChild(CodeComposite target, CodeComponent child, int index){
		target.addChildToIndex(child, index);
	}
	
	public void removeChild(CodeComposite target, int index){
		target.removeChildFromIndex(index);
	}
	
	//Function actions
	public void createParameter(String name, short type ){
		
	}
	
	public void removeParameter(String name){
		
	}
	
	public void createVariable(Function f){
		Variable newVar = (Variable) dataFactory.createVariable();
		newVar.setVariableName("newVar"+f.getVariableID());
		newVar.setVariableType(ModelConstants.VAR_INT_TYPE);
		f.addLocalVariable(newVar);
		notifyVariableCreated(newVar);
	}
	
	public void removeVariable(String name){
		
	}
	
	private void notifyCodeCompositeCreated(DataObject dataObject){
		Services.getService().mapping().addToMap(dataObject.getUniqueID()+"", dataObject);
		setChanged();
		notifyObservers(dataObject.getUniqueID()+"");
	}
	
	private void notifyVariableCreated(Variable var){
	}
	
}

