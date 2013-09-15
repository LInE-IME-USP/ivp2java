package usp.ime.line.ivprog.controller;

import ilm.framework.domain.DomainModel;

import java.util.HashMap;

import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableName;
import usp.ime.line.ivprog.model.domainaction.DeleteVariable;
import usp.ime.line.ivprog.model.domainaction.NewVariable;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPFunctionBody;

public class IVPController {

	private IVPProgram program = null;
	private IVPDomainGUI gui = null;
	private HashMap actionList;
	
	public IVPController(){
		actionList = new HashMap();
	}

	public HashMap getActionList(){
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

	public void addChild(String scopeID, short childType) {
	
	}

	public void addParameter(String scopeID) {

	}

	public void addVariable(String scopeID) {
		NewVariable newVar = (NewVariable) actionList.get("newvar");
		newVar.setScopeID(scopeID);
		newVar.execute();
	}

	public void deleteVariable(String scopeID, String id) {
		DeleteVariable delVar = (DeleteVariable) actionList.get("delvar");
		delVar.setScopeID(scopeID);
		delVar.setVariableID(id);
		delVar.execute();
	}
	
	//TODO: DomainAction
	public void changeVariableName(String id, String name){
		ChangeVariableName changeVarName = (ChangeVariableName) actionList.get("changeVarName");
		changeVarName.setVariableID(id);
		changeVarName.setNewName(name);
		changeVarName.execute();
	}
	
	//TODO: DomainAction
	public void changeVariableInitialValue(String id, String value){
		program.changeVariableInitialValue(id, value);
	}
	
	public void initDomainActionList(DomainModel model) {
		NewVariable newVar = new NewVariable("newvar","newvar");
		newVar.setDomainModel(model);
		actionList.put("newvar", newVar);
		DeleteVariable delVar = new DeleteVariable("delvar", "delvar");
		delVar.setDomainModel(model);
		actionList.put("delvar", delVar);
		ChangeVariableName changeVarName = new ChangeVariableName("changeVarName", "changeVarName");
		changeVarName.setDomainModel(model);
		actionList.put("changeVarName", changeVarName);
	}

}
;