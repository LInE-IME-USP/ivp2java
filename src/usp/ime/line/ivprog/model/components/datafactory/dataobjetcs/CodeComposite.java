package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public abstract class CodeComposite extends CodeComponent {

	private Vector children = new Vector();

	/**
	 * Append a child at the end of children's vector.
	 * @param aChild
	 */
	public void addChild(String aChildID) {
		children.add(aChildID);
	}

	/**
	 * Put the child at the specified position.
	 * @param holdingComponent
	 * @param index
	 */
	public void addChildToIndex(String holdingComponentID, int index) {
		children.add(index, holdingComponentID);
	}

	/**
	 * Remove a child from the specified position and return it.
	 * @param index
	 */
	public String removeChildFromIndex(int index) {
		String removedChildID = (String) children.remove(index);
		return removedChildID;
	}

	/**
	 * Remove the specified child from the children vector and return it.
	 * @param child
	 */
	public String removeChild(String childID) {
		children.remove(childID);
		return childID;
	}

	/**
	 * Return the child at the specified position.
	 * @param i
	 * @return
	 */
	public String getChildAtIndex(int i) {
		return (String) children.get(i);
	}

	/**
	 * Set the children list. It's useful for file reading.
	 * @param childrenList
	 */
	public void setChildrenList(Vector childrenList) {
		children = childrenList;
	}

	/**
	 * Return the children vector.
	 * @return
	 */
	public Vector getChildrenList() {
		return children;
	}

}
