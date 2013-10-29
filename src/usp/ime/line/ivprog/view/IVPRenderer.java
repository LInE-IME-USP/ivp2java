package usp.ime.line.ivprog.view;

import javax.swing.JComponent;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Reference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.AttributionLineUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.ExpressionHolderUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.OperationUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.VariableSelectorUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.WhileUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.PrintUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPRenderer {
	public JComponent paint(Object objectKey) {
		DataObject codeElementModel = (DataObject) Services.getService().getModelMapping().get((String) objectKey);
		if (codeElementModel instanceof Function) {
			return renderFunction((Function) codeElementModel);
		} else if (codeElementModel instanceof Variable) {
			return renderVariable((Variable) codeElementModel);
		} else if (codeElementModel instanceof While){
			return renderWhile((While)codeElementModel);
		} else if (codeElementModel instanceof Print){
			return renderWrite((Print) codeElementModel);
		} else if (codeElementModel instanceof AttributionLine){
			return renderAttributionLine((AttributionLine) codeElementModel);
		} else if (codeElementModel instanceof Expression){
			System.out.println("Expression >");
			return renderExpresion((Expression) codeElementModel);
			
		} else if (codeElementModel instanceof Reference){
			System.out.println("Reference > ");
			return renderReference((Reference)codeElementModel);
		}
		return null;
	}

	private JComponent renderReference(Reference referenceModel) {
		//acho que isso aqui poderia renderizar uma label com o nome da variável...
		//seria a label que vai 
		return null;
	}

	private JComponent renderExpresion(Expression expressionModel) {
		VariableSelectorUI var;
		OperationUI exp;
		System.out.println(expressionModel.getExpressionType());
		if(expressionModel.getExpressionType() == Expression.EXPRESSION_VARIABLE){
			System.out.println("Expression Type == variable");
			var = new VariableSelectorUI(expressionModel.getParentID());
			return var;
		}else{ // It's an operation
			System.out.println("Expression Type == operation");
			exp = new OperationUI(expressionModel.getParentID(), expressionModel.getScopeID());
			exp.setExpressionBaseUI_1((ExpressionHolderUI) paint(((Operation)expressionModel).getExpressionA()));
			//exp.setExpressionBaseUI_2(new ExpressionHolderUI(object.getUniqueID()));
			return exp;
		}
	}

	private JComponent renderAttributionLine(AttributionLine attLineModel) {
		AttributionLineUI attLine = new AttributionLineUI(attLineModel.getUniqueID(), attLineModel.getScopeID(), attLineModel.getParentID());
		attLine.setModelParent(attLineModel.getParentID());
		attLine.setModelScope(attLineModel.getScopeID());
		Services.getService().getViewMapping().put(attLineModel.getUniqueID(), attLine);
		return attLine;
	}

	private JComponent renderWhile(While object) {
		WhileUI w = new WhileUI(object.getUniqueID());
		w.setModelParent(object.getParentID());
		w.setModelScope(object.getScopeID());
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
		PrintUI print = new PrintUI(p.getUniqueID(), p.getParentID(), p.getScopeID());
		print.setModelParent(p.getParentID());
		print.setModelScope(p.getScopeID());
		Services.getService().getViewMapping().put(p.getUniqueID(), print);
		return print;
	}

	private JComponent renderVariable(Variable object) {
		IVPVariableBasic variable = new IVPVariableBasic(object.getUniqueID(), object.getScopeID());
		variable.setVariableName(object.getVariableName());
		Services.getService().getViewMapping().put(object.getUniqueID(), variable);
		return variable;
	}

}
