package usp.ime.line.ivprog.model.components.datafactory;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AskUser;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.AttributionLine;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Comment;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Constant;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.For;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.FunctionReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IVPMatrix;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IVPMatrixReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IVPVector;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IVPVectorReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IfElse;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.ReadData;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.ReturnStatement;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;

public class DataFactory implements IDataFactory {
    /**
     * This integer grants an unique id for each DomainObject
     */
    private int objectID = 0;
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createConstant()
     */
    public DataObject createConstant() {
        Constant cont = new Constant(Constant.STRING_CLASS, Constant.STRING_CLASS);
        cont.setUniqueID("" + objectID++);
        return cont;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createVariable()
     */
    public DataObject createVariable() {
        Variable variable = new Variable(Variable.STRING_CLASS, Variable.STRING_CLASS);
        variable.setUniqueID("" + objectID++);
        return variable;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createIVPArray()
     */
    public DataObject createIVPVector() {
        IVPVector array = new IVPVector(IVPVector.STRING_CLASS, IVPVector.STRING_CLASS);
        array.setUniqueID("" + objectID++);
        return array;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createMatrix()
     */
    public DataObject createIVPMatrix() {
        IVPMatrix matrix = new IVPMatrix(IVPMatrix.STRING_CLASS, IVPMatrix.STRING_CLASS);
        matrix.setUniqueID("" + objectID++);
        return matrix;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createOperation()
     */
    public DataObject createExpression() {
        Operation op = new Operation(Operation.STRING_CLASS, Operation.STRING_CLASS + " " + objectID);
        op.setUniqueID("" + objectID++);
        return op;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createIfElse()
     */
    public DataObject createIfElse() {
        IfElse ifelse = new IfElse(IfElse.STRING_CLASS, IfElse.STRING_CLASS);
        ifelse.setUniqueID("" + objectID++);
        return ifelse;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory#createFor ()
     */
    public DataObject createFor() {
        For f = new For(For.STRING_CLASS, For.STRING_CLASS);
        f.setUniqueID("" + objectID++);
        return f;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createWhile()
     */
    public DataObject createWhile() {
        While w = new While(While.STRING_CLASS, While.STRING_CLASS);
        w.setUniqueID("" + objectID++);
        return w;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createPrint()
     */
    public DataObject createPrint() {
        Print print = new Print(Print.STRING_CLASS, Print.STRING_CLASS);
        print.setUniqueID("" + objectID++);
        return print;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createRead()
     */
    public DataObject createRead() {
        ReadData read = new ReadData(ReadData.STRING_CLASS, ReadData.STRING_CLASS);
        read.setUniqueID("" + objectID++);
        return read;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createComment()
     */
    public DataObject createComment() {
        Comment comment = new Comment(Comment.STRING_CLASS, Comment.STRING_CLASS);
        comment.setUniqueID("" + objectID++);
        return comment;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createAttributionLine()
     */
    public DataObject createAttributionLine() {
        AttributionLine attline = new AttributionLine(AttributionLine.STRING_CLASS, AttributionLine.STRING_CLASS);
        attline.setUniqueID("" + objectID++);
        return attline;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createReturnStatement()
     */
    public DataObject createReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement(ReturnStatement.STRING_CLASS, ReturnStatement.STRING_CLASS);
        returnStatement.setUniqueID("" + objectID++);
        return returnStatement;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createFunction()
     */
    public DataObject createFunction() {
        Function f = new Function(Function.STRING_CLASS, Function.STRING_CLASS);
        f.setUniqueID("" + objectID++);
        return f;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createFunctionReference()
     */
    public DataObject createFunctionReference() {
        FunctionReference fr = new FunctionReference(FunctionReference.STRING_CLASS, FunctionReference.STRING_CLASS);
        fr.setUniqueID("" + objectID++);
        return fr;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createVarReference()
     */
    public DataObject createVarReference() {
        VariableReference varRef = new VariableReference(VariableReference.STRING_CLASS, VariableReference.STRING_CLASS + " " + objectID);
        varRef.setUniqueID("" + objectID++);
        return varRef;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createIVPVectorReference()
     */
    public DataObject createIVPVectorReference() {
        IVPVectorReference vRef = new IVPVectorReference(IVPVectorReference.STRING_CLASS, IVPVectorReference.STRING_CLASS);
        vRef.setUniqueID("" + objectID++);
        return vRef;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createIVPMatrixReference()
     */
    public DataObject createIVPMatrixReference() {
        IVPMatrixReference mRef = new IVPMatrixReference(IVPMatrixReference.STRING_CLASS, IVPMatrixReference.STRING_CLASS);
        mRef.setUniqueID("" + objectID++);
        return mRef;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see usp.ime.line.ivprog.components.databuilder.dataobjetcs.IDataFactory# createAskUser()
     */
    public DataObject createAskUser() {
        AskUser ask = new AskUser(AskUser.STRING_CLASS, AskUser.STRING_CLASS);
        ask.setUniqueID("" + objectID++);
        return ask;
    }
}
