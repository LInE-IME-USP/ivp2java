package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class CodeComponent extends DataObject {

	public CodeComponent(String name, String description) {
		super(name, description);
	}

	private String escopeID;

	public String getEscope() {
		return escopeID;
	}

	public void setEscopeID(String fID) {
		escopeID = fID;
	}

}
