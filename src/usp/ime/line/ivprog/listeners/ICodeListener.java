package usp.ime.line.ivprog.listeners;

public interface ICodeListener {
	
	public void childAdded(String childID);
	public void childRemoved(String childID);
	public void restoreChild(String childID, int index);
	
}
