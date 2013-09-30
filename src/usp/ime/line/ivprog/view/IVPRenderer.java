package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.AttributionLineUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.WhileUI;
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
		} else if (object instanceof AttributionLine){
			return renderAttributionLine((AttributionLine) object);
		}
		return null;
	}

	private JComponent renderAttributionLine(AttributionLine object) {
		AttributionLineUI attLine = new AttributionLineUI(object.getUniqueID(), object.getParentID());
		attLine.setParentID(object.getParentID());
		attLine.setScopeID(object.getScopeID());
		Services.getService().getViewMapping().put(object.getUniqueID(), attLine);
		return attLine;
	}

	private JComponent renderWhile(While object) {
		WhileUI w = new WhileUI(object.getUniqueID());
		w.setParentID(object.getParentID());
		w.setScopeID(object.getScopeID());
		Services.getService().getViewMapping().put(object.getUniqueID(), w);
		return w;
	}

	public FunctionBodyUI renderFunction(Function f) {
		FunctionBodyUI function;
		if (f.getFunctionName().equals(ResourceBundleIVP.getString("mainFunctionName"))) {
			function = new FunctionBodyUI(f.getUniqueID(), true);
		} else
			function = new FunctionBodyUI(f.getUniqueID(), false);
		// parameters and variables need to be rendered
		function.setName(f.getFunctionName());
		function.setType(f.getReturnType());
		Services.getService().getViewMapping().put(f.getUniqueID(), function);
		return function;
	}
	
	private JComponent renderWrite(Print p){
		PrintUI print = new PrintUI(p.getUniqueID());
		print.setParentID(p.getParentID());
		print.setScopeID(p.getScopeID());
		Services.getService().getViewMapping().put(p.getUniqueID(), print);
		return print;
	}

	private JComponent renderVariable(Variable object) {
		IVPVariableBasic variable = new IVPVariableBasic();
		variable.setVariableName(object.getVariableName());
		variable.setID(object.getUniqueID());
		variable.setEscope(object.getScopeID());
		Services.getService().getViewMapping().put(object.getUniqueID(), variable);
		return variable;
	}

}
