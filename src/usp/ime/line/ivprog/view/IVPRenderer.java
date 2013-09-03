package usp.ime.line.ivprog.view;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.domaingui.IVPFunctionBody;

public class IVPRenderer {

	public IVPFunctionBody renderFunction(Function f){
		IVPFunctionBody function = new IVPFunctionBody();
		//parameters and variables need to be rendered
		function.setName(f.getFunctionName());
		function.setType(f.getReturnType());
		return function;
	}
	
}
