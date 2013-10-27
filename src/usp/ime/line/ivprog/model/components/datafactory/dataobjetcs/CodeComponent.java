package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class CodeComponent extends DataObject {

	private String scopeID;

	public CodeComponent(String name, String description) {
		super(name, description);
	}
	
	public String getScopeID() {
		return scopeID;
	}

	public void setScopeID(String scopeID) {
		this.scopeID = scopeID;
	}

}
