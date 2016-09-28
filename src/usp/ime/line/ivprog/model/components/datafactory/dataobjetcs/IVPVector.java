
/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.model.utils.Services;

public class IVPVector extends Collection {

  private int vectorSize = 0;
  private String[] elements = null;
  public static final String STRING_CLASS = "vector";

  public IVPVector(String name, String description) {
    super(name, description);
    }

  /**
   * Put the specified object into the specified array position. If there's a variable at that position it will be overwritten.
   * @param index
   * @param element
   */
  public void addElementToIndex (int index, String elementID) {
    elements[index] = elementID;
    }

  /**
   * Return the element at the specified position in this vector.
   * @param index
   * @return at the specified position
   */
  public String getElementAtIndex (int index) {
    return elements[index];
    }

  /**
   * Return the IVProgVector size.
   * @return the vector size
   */
  public int getVectorSize () {
    return vectorSize;
    }

  /**
   * Sets the IVProgVector size.
   * @param vSize
   */
  public void setVectorSize (int vSize) {
    vectorSize = vSize;
    elements = new String[vSize];
    }

  /**
   * Remove the element from the specified position, return it, and put a null on the elements place.
   * @param index
   */
  public String removeFromIndex (int index) {
    String variableID = elements[index];
    elements[index] = null;
    return variableID;
    }

  public String toXML () {
    String str = "<dataobject class=\"ivpvector\">" + "<id>" + getUniqueID() + "</id>" + "<collectionname>" + getCollectionName()
            + "</collectionname>" + "<collectiontype>" + getCollectionType() + "</collectiontype>" + "<size>" + vectorSize
            + "</size><elements>";
    for (int i = 0; i < vectorSize; i++) {
      Variable anElement = (Variable) Services.getModelMapping().get(elements[i]);
      str += "<element>" + anElement.toXML() + "</element>";
      }
    str += "</elements></dataobject>";
    return str;
    }

  public String toJavaString () {
    return null;
    }

  public boolean equals (DomainObject o) {
    // TODO Auto-generated method stub
    return false;
    }

  }
