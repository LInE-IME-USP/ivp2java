package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IVPFunctionBody;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IVPWhile;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.PrintUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPRenderer {
	public JComponent paint(Object objectKey) {
		DataObject object = (DataObject) Services.getService().getModelMapping().get((String) objectKey);
		if (object instanceof Function) {
			return renderFunction((Function) object);
		} else if (object instanceof Variable) {
			return renderVariable((Variable) object);
		} else if (object instanceof While){
			return renderWhile((While)object);
		} else if (object instanceof Print){
			return renderWrite((Print) object);
		}
			
		return null;
	}

	private JComponent renderWhile(While object) {
		IVPWhile w = new IVPWhile(object.getUniqueID());
		w.setParentID(object.getEscope());
		Services.getService().getViewMapping().put(object.getUniqueID(), w);
		return w;
	}

	public IVPFunctionBody renderFunction(Function f) {
		IVPFunctionBody function;
		if (f.getFunctionName().equals(ResourceBundleIVP.getString("mainFunctionName"))) {
			function = new IVPFunctionBody(f.getUniqueID(), true);
		} else
			function = new IVPFunctionBody(f.getUniqueID(), false);
		// parameters and variables need to be rendered
		function.setName(f.getFunctionName());
		function.setType(f.getReturnType());
		Services.getService().getViewMapping().put(f.getUniqueID(), function);
		return function;
	}
	
	private JComponent renderWrite(Print p){
		PrintUI print = new PrintUI(p.getUniqueID());
		print.setParentID(p.getEscope());
		Services.getService().getViewMapping().put(p.getUniqueID(), print);
		return print;
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
