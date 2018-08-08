package usp.ime.line.ivprog.listeners;

public interface IVariableListener {
	public void addedVariable(String id);

	public void changeVariable(String id);

	public void removedVariable(String id);

	public void changeVariableName(String id, String newName, String lastName);

	public void changeVariableValue(String id, String value);

	public void changeVariableType(String id, short newType);

	public void variableRestored(String id);

	public void updateReference(String id); // the context says if its an
											// "undo/redo/do for the first time"
											// action.
}
