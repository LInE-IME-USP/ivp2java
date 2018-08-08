package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeForMode extends DomainAction {
	private IVPProgram model;
	private int lastMode;
	private int newMode;
	private String forID = "";

	public ChangeForMode(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram) m;
	}

	protected void executeAction() {
		lastMode = model.changeForMode(newMode, forID, _currentState);
	}

	protected void undoAction() {
		model.changeForMode(lastMode, forID, _currentState);
	}

	public boolean equals(DomainAction a) {
		return false;
	}

	public int getNewMode() {
		return newMode;
	}

	public void setNewMode(int newMode) {
		this.newMode = newMode;
	}

	public String getForID() {
		return forID;
	}

	public void setForID(String forID) {
		this.forID = forID;
	}

	public int getLastMode() {
		return lastMode;
	}

	public void setLastMode(int lastMode) {
		this.lastMode = lastMode;
	}

	public String toString() {
		String str = "";
		str += "<changeformode>\n" + "   <forid>" + forID + "</forid>\n" + "   <lastmode>" + lastMode + "</lastmode>\n" + "   <newmode>"
		        + newMode + "</newmode>\n" + "</changeformode>\n";
		return str;
	}
}
