package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public abstract class CodeComposite extends CodeComponent {

	private Vector children = new Vector();

	/**
	 * Append a child at the end of children's vector.
	 * @param aChild
	 */
	public void addChild(CodeComposite aChild) {
		children.add(aChild);
	}

	/**
	 * Put the child at the specified position.
	 * @param holdingComponent
	 * @param index
	 */
	public void addChildToIndex(CodeComponent holdingComponent, int index) {
		children.add(index, holdingComponent);
	}

	/**
	 * Remove a child from the specified position and return it.
	 * @param index
	 */
	public CodeComponent removeChildFromIndex(int index) {
		CodeComponent removedChild = (CodeComponent) children.get(index);
		children.remove(index);
		return removedChild;
	}

	/**
	 * Remove the specified child from the children vector and return it.
	 * @param child
	 */
	public CodeComponent removeChild(CodeComposite child) {
		children.remove(child);
		return child;
	}

	/**
	 * Return the child at the specified position.
	 * @param i
	 * @return
	 */
	public CodeComponent getChildAtIndex(int i) {
		return (CodeComponent) children.get(new Integer(i).intValue());
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
