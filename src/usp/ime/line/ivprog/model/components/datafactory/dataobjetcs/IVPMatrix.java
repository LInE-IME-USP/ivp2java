package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.awt.Point;

import usp.ime.line.ivprog.Services;

public class IVPMatrix extends Collection {

	private Point dimension = null;
	private String[][] elements = null;
	private int nLines = -1;
	private int nColumns = -1;
	public static final String STRING_CLASS = "matrix";

	public IVPMatrix(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Set the matrix dimensions number of columns (nC) and number of lines
	 * (nL).
	 * @param nC
	 * @param nL
	 */
	public void setMatrixDimension(Point dimension) {
		nLines = new Double(dimension.getX()).intValue();
		nColumns = new Double(dimension.getY()).intValue();
		elements = new String[nLines][nColumns];
	}

	/**
	 * Put the specified element into the specified position of the matrix. If
	 * there's a variable at that place it will be overwritten.
	 * @param column
	 * @param line
	 * @param element
	 */
	public void addToIndexes(Point index, String elementID) {
		int x = new Double(index.getX()).intValue();
		int y = new Double(index.getY()).intValue();
		elements[x][y] = elementID;
	}

	/**
	 * Returns the element at the specified position.
	 * @param column
	 * @param line
	 * @return
	 */
	public String getElementFromIndex(Point index) {
		int x = new Double(index.getX()).intValue();
		int y = new Double(index.getY()).intValue();
		return elements[x][y];
	}

	/**
	 * Remove the element at the specified position and put null at that
	 * position and return it.
	 * @param column
	 * @param line
	 */
	public String removeElementFromIndex(int column, int line) {
		String variableID = elements[line][column];
		elements[line][column] = "";
		return variableID;
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
				Variable anElement = (Variable) Services.getService().getModelMapping().get(elements[i][j]);
				
				
				str += "<A" + i + "" + j + ">" + anElement.toXML()
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
	 * Get the point that carries the matrix dimension with x = lines and y =
	 * columns
	 * @return
	 */
	public Point getDimension() {
		return dimension;
	}

	/**
	 * Set the point that carries the matrix dimension with x = lines and y =
	 * columns
	 * @param dimension
	 */
	public void setDimension(Point dimension) {
		this.dimension = dimension;
	}

	@Override
	public boolean equals(DomainObject o) {
		// TODO Auto-generated method stub
		return false;
	}

}
