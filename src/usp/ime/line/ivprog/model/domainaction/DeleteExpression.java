package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class DeleteExpression extends DomainAction {
	
	private String holder;
	private String expression;
	private IVPProgram model;

	public DeleteExpression(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram)m;
	}

	protected void executeAction() {
		model.deleteExpression(expression, holder, _currentState);
	}

	protected void undoAction() {
		model.restoreExpression(expression, holder, _currentState);
	}

	public boolean equals(DomainAction a) {
		return false;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
