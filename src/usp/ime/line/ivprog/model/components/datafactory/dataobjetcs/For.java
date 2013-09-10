package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.controller.Services;

public class For extends CodeComposite {

	private String indexInitialValue = null;
	private String upperBound = null;
	private String increment = null;

	/**
	 * Returns the expression containing index initial value.
	 * @return
	 */
	public String getIndexInitialValue() {
		return indexInitialValue;
	}

	/**
	 * Set the index initial value expression.
	 * @param indexInitValue
	 */
	public void setIndexInitialValue(String indexInitValue) {
		indexInitialValue = indexInitValue;
	}

	/**
	 * Return the index upper bound's expression.
	 * @return
	 */
	public String getUpperBound() {
		return upperBound;
	}

	/**
	 * Sets the upper bound's expression.
	 * @param hBound
	 */
	public void setUpperBound(String hBound) {
		upperBound = hBound;
	}

	/**
	 * Return the expression that defines the increment's value.
	 * 
	 * @return
	 */
	public String getIncrement() {
		return increment;
	}

	/**
	 * Set the increment's value.
	 * 
	 * @param inc
	 */
	public void setIncrement(String inc) {
		increment = inc;
	}

	public String toXML() {
		Expression index = (Expression) Services.getService().mapping().getObject(indexInitialValue);
		Expression upper = (Expression) Services.getService().mapping().getObject(upperBound);
		Expression inc = (Expression) Services.getService().mapping().getObject(increment);
		String str = "<dataobject class=\"for\">" + "<id>" + getUniqueID()
				+ "</id>" + "<initialvalue>" + index.toXML()
				+ "</initialvalue>" + "<upperbound>" + upper.toXML()
				+ "</upperbound>" + "<increment>" + inc.toXML()
				+ "</increment>" + "<children>";
		Vector children = getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			str += ((DataObject)  Services.getService().mapping().getObject((String) children.get(i))).toXML();
		}
		str += "</children></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
 