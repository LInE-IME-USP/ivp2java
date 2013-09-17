package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPFunctionBody;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IVPWhile;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPRenderer {
	//teste
	public JComponent paint(Object objectKey) {
		DataObject object = (DataObject) Services.getService().getModelMapping().get((String) objectKey);
		System.out.println("DataObject "+object);
		
		if (object instanceof Function) {
			System.out.println("DEVERIA TER IDENTIFICADO UMA FUNÇÃO");
			return renderFunction((Function) object);
		} else if (object instanceof Variable) {
			return renderVariable((Variable) object);
		} else if (object instanceof While){
			return renderWhile((While)object);
		}
		return null;
	}

	private JComponent renderWhile(While object) {
		return new IVPWhile();
	}

	public IVPFunctionBody renderFunction(Function f) {
		IVPFunctionBody function;
		if (f.getFunctionName().equals(
				ResourceBundleIVP.getString("mainFunctionName"))) {
			function = new IVPFunctionBody(f.getUniqueID(), true);
		} else
			function = new IVPFunctionBody(f.getUniqueID(), false);
		// parameters and variables need to be rendered
		function.setName(f.getFunctionName());
		function.setType(f.getReturnType());
		return function;
	}

	private JComponent renderVariable(Variable object) {
		IVPVariableBasic variable = new IVPVariableBasic();
		variable.setVariableName(object.getVariableName());
		variable.setID(object.getUniqueID());
		variable.setEscope(object.getEscopeID());
		Services.getService().getViewMapping().put(object.getUniqueID(), variable);
		return variable;
	}

}
