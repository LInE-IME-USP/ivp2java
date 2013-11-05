package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateExpression extends DomainAction{
	
	private IVPProgram model;
	private String holder;
	private String exp1;
	private String newExpression;
	private String scopeID;
	/*
	 * New expressions are like (exp1+ SIGN expSpace+)+
	 */
	private short expressionType;

	public CreateExpression(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram)m;
	}

	protected void executeAction() {
		newExpression = model.createExpression(exp1, holder, scopeID, expressionType, _currentState);
	}

	protected void undoAction() {
		
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

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}
	
	public short getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(short expressionType) {
		this.expressionType = expressionType;
	}

	public String getScopeID() {
		return scopeID;
	}

	public void setScopeID(String scopeID) {
		this.scopeID = scopeID;
	}

}
