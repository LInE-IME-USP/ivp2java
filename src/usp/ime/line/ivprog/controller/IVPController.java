package usp.ime.line.ivprog.controller;

import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.domaingui.IVPFunctionBody;

public class IVPController {

	private IVPProgram program = null;
	private IVPDomainGUI gui = null;

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
		program.addObserver(gui);
		program.initializeModel();
	}

	public void showExecutionEnvironment() {
	
	}

	public void showConstructionEnvironment() {
	
	}

	public void addChild(CodeComposite container, short childType) {
	
	}

	public void addParameter(Function f) {

	}

	public void addVariable(Function f) {
		program.createVariable(f);
	}

	public void deleteVariable(String escopeID, String id) {
		program.removeVariable(escopeID, id);
	}
	public void changeVariableName(String id, String name){
		program.changeVariableName(id, name);
	}

}
