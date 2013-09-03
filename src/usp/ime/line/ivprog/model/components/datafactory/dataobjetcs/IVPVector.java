package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class IVPVector extends Collection {

	private int vectorSize = 0;
	private Variable[] elements = null;

	/**
	 * Put the specified object into the specified array position. If there's a
	 * variable at that position it will be overwritten.
	 * 
	 * @param index
	 * @param element
	 */
	public void addElementToIndex(int index, Variable element) {
		elements[index] = element;
	}

	/**
	 * Return the element at the specified position in this vector.
	 * 
	 * @param index
	 * @return at the specified position
	 */
	public Variable getElementAtIndex(int index) {
		return elements[index];
	}

	/**
	 * Return the IVProgVector size.
	 * 
	 * @return the vector size
	 */
	public int getVectorSize() {
		return vectorSize;
	}

	/**
	 * Sets the IVProgVector size.
	 * 
	 * @param vSize
	 */
	public void setVectorSize(int vSize) {
		vectorSize = vSize;
		elements = new Variable[vSize];
	}

	/**
	 * Remove the element from the specified position, return it, and put a null
	 * on the elements place.
	 * 
	 * @param index
	 */
	public DataObject removeFromIndex(int index) {
		DataObject variable = elements[index];
		elements[index] = null;
		return variable;
	}

	public String toXML() {
		String str = "<dataobject class=\"ivpvector\">" + "<id>"
				+ getUniqueID() + "</id>" + "<collectionname>"
				+ getCollectionName() + "</collectionname>"
				+ "<collectiontype>" + getCollectionType()
				+ "</collectiontype>" + "<size>" + vectorSize
				+ "</size><elements>";
		for (int i = 0; i < vectorSize; i++) {
			str += "<element>" + elements[i].toString() + "</element>";
		}
		str += "</elements></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

}
