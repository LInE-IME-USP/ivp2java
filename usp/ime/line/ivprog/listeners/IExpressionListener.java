package usp.ime.line.ivprog.listeners;

public interface IExpressionListener {
	public void expressionCreated(String lastExp, String id, String context);

	public void expressionDeleted(String id, String context, boolean isClean);

	public void expressionRestored(String holder, String id, String context);

	public void expressionRestoredFromCleaning(String holder, String id, String context);
}
