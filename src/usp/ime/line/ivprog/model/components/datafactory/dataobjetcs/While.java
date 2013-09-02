package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class While extends CodeComposite {

	private Operation condition = null;

	/**
	 * Return the loop condition.
	 * 
	 * @return
	 */
	public Operation getCondition() {
		return condition;
	}

	/**
	 * Set the loop condition.
	 * 
	 * @param cond
	 */
	public void setCondition(Operation cond) {
		condition = cond;
	}

	public String toXML() {
		String str = "<dataobject class=\"while\"><id>" + getUniqueID()
				+ "</id>" + "<condition>" + condition.toXML()
				+ "</condition><children>";
		for (int i = 0; i < getChildrenList().size(); i++) {
			str += ((DataObject) getChildAtIndex(i)).toXML();
		}
		str += "</children></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
