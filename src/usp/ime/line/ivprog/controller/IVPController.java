package usp.ime.line.ivprog.controller;

import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;

public class IVPController {

	private static IVPController instance = null;
	private IVPProgram program = null;
	private IVPDomainGUI gui = null;

	public static IVPController getInstance(){
		if(instance != null)
			return instance;
		else 
			instance = new IVPController();
		return instance;
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
		program.addObserver(gui);
		program.initializeModel();			
	}
	
	public void showExecutionEnvironment(){
		System.out.println("show execution environment");
	}
	
	public void showConstructionEnvironment(){
		System.out.println("show construction environment");
	}
}
