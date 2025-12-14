package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtAssign extends AstStmt
{
	public AstVar var;
	public AstExp exp;

	public AstStmtAssign(AstVar var, AstExp exp, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> var := exp ;\n");
		// // x := y + 1;
		
		this.var = var;
		this.exp = exp;
	}

	public void printMe()
	{
		System.out.print("AST STMT ASSIGN\n");

		if (var != null) var.printMe();
		if (exp != null) exp.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"ASSIGN\nleft := right\n");
		
		AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
		AstGraphviz.getInstance().logEdge(serialNumber,exp.serialNumber);
	}

	public Type semantMe()
	{
		Type t1 = null;
		Type t2 = null;
		
		if (var != null) t1 = var.semantMe();
		if (exp != null) t2 = exp.semantMe();
		
		// Special case for arrays: if exp is "new T[e]", check if var is an array defined over T
		// This handles the exception mentioned in 2.4: "For arrays, if e = new T, then x must be 
		// of type array defined over type T"

		// array IntArray = int[];
		// IntArray arr;
		// arr := new int[5];  // Special case, handled here
		// 
		// IntArray arr2 := arr;  // General case, handled by isCompatibleWith
		
		if (t1 instanceof TypeArray && t2 instanceof TypeArray && exp instanceof AstNewExpArr) {
			TypeArray arrayType = (TypeArray) t1;
			TypeArray newArrayType = (TypeArray) t2;
			// Check if the var's array is defined over the same element type as the new expression
			if (arrayType.arrayDataType == newArrayType.arrayDataType) {
				return null; // Compatible - special case satisfied
			}
		}
		
        if (!t1.isCompatibleWith(t2))
			throw new RuntimeException("semantic error: assignment type mismatch");
		return null;
	}
}