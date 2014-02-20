package usp.ime.line.ivprog.listeners;

public interface ICodeListener {
    public void addChild(String childID, String context);
    
    public void childRemoved(String childID, String context);
    
    public void restoreChild(String childID, int index, String context);
}
