package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.awt.Point;

public class IVPMatrix extends Collection {

	private Point dimension = null;
	private Variable[][] elements = null;
	private int nLines = -1;
	private int nColumns = -1;

	/**
	 * Set the matrix dimensions number of columns (nC) and number of lines
	 * (nL).
	 * 
	 * @param nC
	 * @param nL
	 */
	public void setMatrixDimension(Point dimension) {
		nLines = new Double(dimension.getX()).intValue();
		nColumns = new Double(dimension.getY()).intValue();
		elements = new Variable[nLines][nColumns];
	}

	/**
	 * Put the specified element into the specified position of the matrix. If
	 * there's a variable at that place it will be overwritten.
	 * 
	 * @param column
	 * @param line
	 * @param element
	 */
	public void addToIndexes(Point index, Variable element) {
		int x = new Double(index.getX()).intValue();
		int y = new Double(index.getY()).intValue();
		elements[x][y] = element;
	}

	/**
	 * Returns the element at the specified position.
	 * 
	 * @param column
	 * @param line
	 * @return
	 */
	public Variable getElementFromIndex(Point index) {
		int x = new Double(index.getX()).intValue();
		int y = new Double(index.getY()).intValue();
		return elements[x][y];
	}

	/**
	 * Remove the element at the specified position and put null at that
	 * position and return it.
	 * 
	 * @param column
	 * @param line
	 */
	public DataObject removeElementFromIndex(int column, int line) {
		DataObject variable = elements[line][column];
		elements[line][column] = null;
		return variable;
	}

	public String toXML() {
		String str = "<dataobject class=\"ivpmatrix\">" + "<id>"
				+ getUniqueID() + "</id>" + "<collectionname>"
				+ getCollectionName() + "</collectionname>"
				+ "<collectiontype>" + getCollectionType()
				+ "</collectiontype>" + "<numberoflines>" + nLines
				+ "</numberoflines><numberofcolumns>" + nColumns
				+ "</numberofcolumns><elements>";
		for (int i = 0; i < nLines; i++) {
			for (int j = 0; j < nColumns; j++) {
				str += "<A" + i + "" + j + ">" + elements[i][j].toString()
						+ "</A" + i + "" + j + ">";
			}
		}
		str += "</elements></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

	/**
	 * Get the point that carries the matrix dimension with x = lines and y = columns
	 * @return
	 */
	public Point getDimension() {
		return dimension;
	}
	
	/**
	 * Set the point that carries the matrix dimension with x = lines and y = columns
	 * @param dimension
	 */
	public void setDimension(Point dimension) {
		this.dimension = dimension;
	}

}
