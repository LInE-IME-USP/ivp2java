package usp.ime.line.ivprog.listeners;

public interface IExpressionListener {
	public void expressionCreated(String lastExp, String id);
	public void expressionDeleted(String id);
	public void expressionRestored(String holder, String id);
}
