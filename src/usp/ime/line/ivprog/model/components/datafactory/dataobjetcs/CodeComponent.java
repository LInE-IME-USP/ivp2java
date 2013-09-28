package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class CodeComponent extends DataObject {

	private String escopeID;

	public String getEscope() {
		return escopeID;
	}

	public void setEscopeID(String fID) {
		escopeID = fID;
	}

}
