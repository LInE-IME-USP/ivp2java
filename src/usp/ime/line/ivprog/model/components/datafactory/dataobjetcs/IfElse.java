package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public class IfElse extends CodeComposite {

	private Vector elseChildren = new Vector();
	private Operation comparison = null;

	/**
	 * Append a child at the specified position of elseChildren's vector.
	 * 
	 * @param aChild
	 */
	public void addElseChildToIndex(CodeComponent aChild, int index) {
		elseChildren.add(index, aChild);
	}

	/**
	 * Remove a child from the specified position and return it.
	 * 
	 * @param index
	 */
	public CodeComposite removeElseChildFromIndex(int index) {
		CodeComposite elseChild = (CodeComposite) elseChildren.get(index);
		elseChildren.remove(index);
		return elseChild;
	}

	/**
	 * Return the child at the specified position.
	 * 
	 * @param index
	 * @return
	 */
	public CodeComponent getElseChildAtIndex(int index) {
		return (CodeComponent) elseChildren.get(index);
	}

	/**
	 * Return the if/else condition.
	 * 
	 * @return
	 */
	public Operation getComparison() {
		return comparison;
	}

	/**
	 * Set the if/else condition.
	 * 
	 * @param comp
	 */
	public void setComparison(Operation comp) {
		comparison = comp;
	}

	public String toXML() {
		String str = "<dataobject class=\"ifelse\">" + "<id>" + getUniqueID()
				+ "</id>" + "<comparison>" + comparison.toXML()
				+ "</comparison>" + "<ifchildren>";
		for (int i = 0; i < getChildrenList().size(); i++) {
			str += ((DataObject) getChildrenList().get(i)).toXML();
		}
		str += "</ifchildren><elsechildren>";
		for (int i = 0; i < elseChildren.size(); i++) {
			str += ((DataObject) elseChildren.get(i)).toXML();
		}
		str += "</elsechildren></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
