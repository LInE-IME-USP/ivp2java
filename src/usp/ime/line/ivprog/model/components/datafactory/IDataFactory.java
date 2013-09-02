package usp.ime.line.ivprog.model.components.datafactory;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;

public interface IDataFactory {

	/**
	 * Return a DataObject that represents a constant.
	 * 
	 * @return
	 */
	public DataObject createConstant();

	/**
	 * Return a DataObject that represents a variable.
	 * 
	 * @return
	 */
	public DataObject createVariable();

	/**
	 * Return a DataObject that represents a vector.
	 * 
	 * @return
	 */
	public DataObject createIVPVector();

	/**
	 * Return a DataObject that represents a matrix.
	 * 
	 * @return
	 */
	public DataObject createIVPMatrix();

	/**
	 * Return a DataObject that represents the operation. It might be algebraic
	 * or boolean.
	 * 
	 * @return
	 */
	public DataObject createOperation();

	/**
	 * Return a DataObject that represents the IfElse domain object.
	 * 
	 * @return
	 */
	public DataObject createIfElse();

	/**
	 * Return a DataObject that represents the For domain object.
	 * 
	 * @return
	 */
	public DataObject createFor();

	/**
	 * Return a DataObject that represents the While domain object.
	 * 
	 * @return
	 */
	public DataObject createWhile();

	/**
	 * Return a DataObject that represents the Print domain object.
	 * 
	 * @return
	 */
	public DataObject createPrint();

	/**
	 * Return a DataObject that represents the Comment domain object.
	 * 
	 * @return
	 */
	public DataObject createComment();

	/**
	 * Return a DataObject that represents the AttibutionLine domain object.
	 * 
	 * @return
	 */
	public DataObject createAttributionLine();

	/**
	 * Return a DataObject that represents the ReturnStatement domain object.
	 * 
	 * @return
	 */
	public DataObject createReturnStatement();

	/**
	 * Return a DataObject that represents the Function domain object.
	 * 
	 * @return
	 */
	public DataObject createFunction();

	/**
	 * Return a DataObject that represents a reference to a function.
	 * 
	 * @return
	 */
	public DataObject createFunctionReference();

	/**
	 * Return a DataObject that represents a reference to a variable.
	 * 
	 * @return
	 */
	public DataObject createVarReference();

	/**
	 * Return a DataObject that represents a reference to a Vector.
	 * 
	 * @return
	 */
	public DataObject createIVPVectorReference();

	/**
	 * Return a DataObject that represents a reference to a Matrix.
	 * 
	 * @return
	 */
	public DataObject createIVPMatrixReference();

	/**
	 * Return a DataObject that represents ask user domain object.
	 * 
	 * @return
	 */
	public DataObject createAskUser();
}