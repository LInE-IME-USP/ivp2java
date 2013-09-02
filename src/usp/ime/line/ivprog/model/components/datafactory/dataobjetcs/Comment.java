package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;


public class Comment extends CodeComponent {

	private String comment = "";

	/**
	 * Return the string containing the comment.
	 * 
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set the comment text of this object.
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String toXML() {
		String str = "<dataobject class=\"comment\">" + "<id>" + getUniqueID()
				+ "</id>" + "<comment>" + comment + "</comment>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
