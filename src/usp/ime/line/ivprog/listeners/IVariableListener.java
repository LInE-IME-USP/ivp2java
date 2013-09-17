package usp.ime.line.ivprog.listeners;

public interface IVariableListener {
	public void addedVariable(String id);
	public void changeVariable(String id);
	public void removedVariable(String id);
	public void changeVariableName(String id, String name);
	public void changeVariableValue(String id, String value);
	public void changeVariableType(String id, short type);
}
