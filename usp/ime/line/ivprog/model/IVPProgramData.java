package usp.ime.line.ivprog.model;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import usp.ime.line.ivprog.model.components.datafactory.DataFactory;

public class IVPProgramData {
	
	private HashMap globalVariables;
	private HashMap preDefinedFunctions;
	private HashMap functionMap;
	private DataFactory dataFactory;
	private List variableListeners;
	private List functionListeners;
	private List expressionListeners;
	private List operationListeners;
	private HashMap codeListeners;
	private HashMap modelHash;
	private HashMap viewHash;

	public IVPProgramData() {
		setGlobalVariables(new HashMap());
		setPreDefinedFunctions(new HashMap());
		setCodeListeners(new HashMap());
		setFunctionMap(new HashMap());
		setDataFactory(new DataFactory());
		setVariableListeners(new Vector());
		setFunctionListeners(new Vector());
		setExpressionListeners(new Vector());
		setOperationListeners(new Vector());
		setModelHash(new HashMap());
		setViewHash(new HashMap());
	}

	public HashMap getGlobalVariables() {
		return globalVariables;
	}

	public void setGlobalVariables(HashMap globalVariables) {
		this.globalVariables = globalVariables;
	}

	public HashMap getPreDefinedFunctions() {
		return preDefinedFunctions;
	}

	public void setPreDefinedFunctions(HashMap preDefinedFunctions) {
		this.preDefinedFunctions = preDefinedFunctions;
	}

	public HashMap getFunctionMap() {
		return functionMap;
	}

	public void setFunctionMap(HashMap functionMap) {
		this.functionMap = functionMap;
	}

	public DataFactory getDataFactory() {
		return dataFactory;
	}

	public void setDataFactory(DataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}

	public List getVariableListeners() {
		return variableListeners;
	}

	public void setVariableListeners(List variableListeners) {
		this.variableListeners = variableListeners;
	}

	public List getFunctionListeners() {
		return functionListeners;
	}

	public void setFunctionListeners(List functionListeners) {
		this.functionListeners = functionListeners;
	}

	public List getExpressionListeners() {
		return expressionListeners;
	}

	public void setExpressionListeners(List expressionListeners) {
		this.expressionListeners = expressionListeners;
	}

	public List getOperationListeners() {
		return operationListeners;
	}

	public void setOperationListeners(List operationListeners) {
		this.operationListeners = operationListeners;
	}

	public HashMap getCodeListeners() {
		return codeListeners;
	}

	public void setCodeListeners(HashMap codeListeners) {
		this.codeListeners = codeListeners;
	}

	public HashMap getModelHash() {
		return modelHash;
	}

	public void setModelHash(HashMap modelHash) {
		this.modelHash = modelHash;
	}

	public HashMap getViewHash() {
		return viewHash;
	}

	public void setViewHash(HashMap viewHash) {
		this.viewHash = viewHash;
	}
}