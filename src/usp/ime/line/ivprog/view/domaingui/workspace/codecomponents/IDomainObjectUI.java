package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

public interface IDomainObjectUI {
	
	public String getModelID();
	public String getModelParent();
	public String getModelScope();

	public void setModelID(String id);
	public void setModelParent(String id);
	public void setModelScope(String id);
	
}
