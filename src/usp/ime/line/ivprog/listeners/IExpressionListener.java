package usp.ime.line.ivprog.listeners;

public interface IExpressionListener {
	public void expressionCreated(String lastExp, String id, String context);
	public void expressionDeleted(String id, String context);
	public void expressionRestored(String holder, String id, String context);
	public void expressionTypeChanged(String id, String context);
}
