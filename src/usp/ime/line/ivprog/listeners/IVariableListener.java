package usp.ime.line.ivprog.listeners;

public interface IVariableListener {
	public void addedVariable(String id);
	public void changeVariable(String id);
	public void removedVariable(String id);
	public void changeVariableName(String id, String newName, String lastName);
	public void changeVariableValue(String id, String value);
	public void changeVariableType(String id, short type);
	public void variableRestored(String id);
}
