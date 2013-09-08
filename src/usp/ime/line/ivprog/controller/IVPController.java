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
		System.out.println(program);
		this.program = program;
	}

	public IVPDomainGUI getGui() {
		return gui;
	}

	public void setGui(IVPDomainGUI gui) {
		System.out.println(gui);
		this.gui = gui;
	}

	public void initializeModel() {
		System.out.println("1"+program+"2"+gui);
		program.addObserver(gui);
		program.initializeModel();			
	}
	
	public void showExecutionEnvironment(){
		System.out.println("show execution environment");
	}
	
	public void showConstructionEnvironment(){
		System.out.println("show construction environment");
	}
	
	public void addChild(CodeComposite container, short childType){
		System.out.println("Add child to container: "+childType);
	}

	public void addParameter(Function f) {
		System.out.println("Add parameter to: "+f.getFunctionName());
	}
	
	public void addVariable(Function f) {
		program.createVariable(f);
		System.out.println("Add variable to: "+f.getFunctionName());
	}
	
	
}
