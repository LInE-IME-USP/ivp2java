package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class CodeComponent extends DataObject {

	private Expression escopeID;

	public Expression getEscope() {
		return escopeID;
	}

	public void setEscopeID(Expression fID) {
		escopeID = fID;
	}

}
