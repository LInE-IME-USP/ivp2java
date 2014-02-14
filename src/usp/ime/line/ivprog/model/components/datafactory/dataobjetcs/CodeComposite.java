package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public abstract class CodeComposite extends CodeComponent {
    private Vector children = new Vector();
    
    public CodeComposite(String name, String description) {
        super(name, description);
    }
    
    /**
     * Append a child at the end of children's vector.
     * 
     * @param aChild
     */
    public void addChild(String aChildID) {
        children.add(aChildID);
    }
    
    /**
     * Put the child at the specified position.
     * 
     * @param holdingComponent
     * @param index
     * @return
     */
    public int addChildToIndex(String holdingComponentID, int index) {
        int lastIndex = -1;
        if (children.contains(holdingComponentID)) {
            lastIndex = children.indexOf(holdingComponentID);
            if (index >= lastIndex) {
                children.add(index, holdingComponentID);
                children.remove(lastIndex);
            } else {
                children.remove(holdingComponentID);
                children.add(index, holdingComponentID);
            }
            return lastIndex;
        } else {
            children.add(index, holdingComponentID);
            return lastIndex;
        }
    }
    
    /**
     * Remove a child from the specified position and return it.
     * 
     * @param index
     */
    public String removeChildFromIndex(int index) {
        String removedChildID = (String) children.remove(index);
        return removedChildID;
    }
    
    /**
     * Remove the specified child from the children vector and return it.
     * 
     * @param child
     */
    public int removeChild(String childID) {
        int index = children.indexOf(childID);
        children.remove(childID);
        return index;
    }
    
    /**
     * Return the child at the specified position.
     * 
     * @param i
     * @return
     */
    public String getChildAtIndex(int i) {
        return (String) children.get(i);
    }
    
    /**
     * Set the children list. It's useful for file reading.
     * 
     * @param childrenList
     */
    public void setChildrenList(Vector childrenList) {
        children = childrenList;
    }
    
    /**
     * Return the children vector.
     * 
     * @return
     */
    public Vector getChildrenList() {
        return children;
    }
}
