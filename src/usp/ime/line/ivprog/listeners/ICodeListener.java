package usp.ime.line.ivprog.listeners;

public interface ICodeListener {
	public void addChild(String childID, String context);

	public void childRemoved(String childID, String context);

	public void moveChild(String childID, String context, int index);

	public void restoreChild(String childID, int index, String context);
}
