package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;


import usp.ime.line.ivprog.controller.Services;

public class IfElse extends CodeComposite {

	private Vector elseChildren = new Vector();
	private String comparisonID = null;

	/**
	 * Append a child at the specified position of elseChildren's vector.
	 * @param aChild
	 */
	public void addElseChildToIndex(String aChildID, int index) {
		elseChildren.add(index, aChildID);
	}

	/**
	 * Remove a child from the specified position and return it.
	 * @param index
	 */
	public String removeElseChildFromIndex(int index) {
		String elseChild = (String) elseChildren.get(index);
		elseChildren.remove(index);
		return elseChild;
	}

	/**
	 * Return the child at the specified position.
	 * @param index
	 * @return
	 */
	public String getElseChildAtIndex(int index) {
		return (String) elseChildren.get(index);
	}

	/**
	 * Return the if/else condition.
	 * @return
	 */
	public String getComparisonID() {
		return comparisonID;
	}

	/**
	 * Set the if/else condition.
	 * @param comp
	 */
	public void setComparison(String compID) {
		comparisonID = compID;
	}

	public String toXML() {
		Operation comp = (Operation) Services.getService().mapping().getObject(comparisonID);
		String str = "<dataobject class=\"ifelse\">" + "<id>" + getUniqueID()
				+ "</id>" + "<comparison>" + comp.toXML()
				+ "</comparison>" + "<ifchildren>";
		
		
		for (int i = 0; i < getChildrenList().size(); i++) {
			CodeComposite child = (CodeComposite) Services.getService().mapping().getObject((String) getChildrenList().get(i));
			str += child.toXML();
		}
		str += "</ifchildren><elsechildren>";
		for (int i = 0; i < elseChildren.size(); i++) {
			CodeComposite child = (CodeComposite) Services.getService().mapping().getObject((String) elseChildren.get(i));
			str += child.toXML();
		}
		str += "</elsechildren></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
