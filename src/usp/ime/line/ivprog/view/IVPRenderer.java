package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.controller.IVPMapping;
import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.domaingui.IVPFunctionBody;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPRenderer {

	public IVPFunctionBody renderFunction(Function f){
		IVPFunctionBody function;
		if(f.getFunctionName().equals(ResourceBundleIVP.getString("mainFunctionName"))){
			function = new IVPFunctionBody(f, true);
		}
		else
			function = new IVPFunctionBody(f, false);
		//parameters and variables need to be rendered
		function.setName(f.getFunctionName());
		function.setType(f.getReturnType());
		return function;
	}

	public JComponent paint(Object objectKey) {
		DataObject object = (DataObject) Services.getService().mapping().getObject((String) objectKey);
		if(object instanceof Function){
			return renderFunction((Function) object);
		}
		return null;
	}
	
}
