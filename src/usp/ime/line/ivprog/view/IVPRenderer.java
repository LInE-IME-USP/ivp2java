package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.utils.IVPMapping;
import usp.ime.line.ivprog.view.domaingui.IVPFunctionBody;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPRenderer {

	public JComponent paint(Object objectKey) {
		DataObject object = (DataObject) Services.getService().mapping().getObject((String) objectKey);
		if (object instanceof Function) {
			return renderFunction((Function) object);
		} else if (object instanceof Variable) {
			return renderVariable((Variable) object);
		}
		return null;
	}

	public IVPFunctionBody renderFunction(Function f) {
		IVPFunctionBody function;
		if (f.getFunctionName().equals(
				ResourceBundleIVP.getString("mainFunctionName"))) {
			function = new IVPFunctionBody(f, true);
		} else
			function = new IVPFunctionBody(f, false);
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
		return variable;
	}

}
