package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public class For extends CodeComposite {

	private Expression indexInitialValue = null;
	private Expression upperBound = null;
	private Expression increment = null;


	/**
	 * Returns the expression containing index initial value.
	 * 
	 * @return
	 */
	public Expression getIndexInitialValue() {
		return indexInitialValue;
	}

	/**
	 * Set the index initial value expression.
	 * 
	 * @param indexInitValue
	 */
	public void setIndexInitialValue(Expression indexInitValue) {
		indexInitialValue = indexInitValue;
	}

	/**
	 * Return the index upper bound's expression.
	 * 
	 * @return
	 */
	public Expression getUpperBound() {
		return upperBound;
	}

	/**
	 * Sets the upper bound's expression.
	 * 
	 * @param hBound
	 */
	public void setUpperBound(Expression hBound) {
		upperBound = hBound;
	}

	/**
	 * Return the expression that defines the increment's value.
	 * 
	 * @return
	 */
	public Expression getIncrement() {
		return increment;
	}

	/**
	 * Set the increment's value.
	 * 
	 * @param inc
	 */
	public void setIncrement(Expression inc) {
		increment = inc;
	}

	public String toXML() {
		String str = "<dataobject class=\"for\">" + "<id>" + getUniqueID()
				+ "</id>" + "<initialvalue>" + indexInitialValue.toXML()
				+ "</initialvalue>" + "<upperbound>" + upperBound.toXML()
				+ "</upperbound>" + "<increment>" + increment.toXML()
				+ "</increment>" + "<children>";
		Vector children = getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			str += ((DataObject) children.get(i)).toXML();
		}
		str += "</children></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
