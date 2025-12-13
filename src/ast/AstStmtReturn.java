package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtReturn extends AstStmt
{
	public AstExp exp;

	public AstStmtReturn(AstExp exp)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		this.exp = exp;
	}

	public void printMe()
	{
		System.out.print("AST NODE STMT RETURN\n");

		if (exp != null) exp.printMe();

		AstGraphviz.getInstance().logNode(
                serialNumber,
			"RETURN");

		if (exp != null) AstGraphviz.getInstance().logEdge(serialNumber,exp.serialNumber);
	}

	public Type semantMe(){
		/*
			According to the syntax of L, return statements can only be found inside functions.
			Since functions can not be nested, it follows that a return statement belongs to exactly one function.
			• If a function has return type void, its return statements must be empty (return;).
			• If a function has a non-void return type T, then every return statement must return an expression
			whose type is compatible with type T.
		*/
		Type returnType = SymbolTable.getInstance().find("__RET_TYPE__");
		if (returnType == null) throw new RuntimeException("semantic error");

		Type expType = null;
		if (exp != null) expType = exp.semantMe();

		if (returnType == TypeVoid.getInstance() && exp != null) throw new RuntimeException("semantic error");

		if (exp == null) return null;

		if (expType == TypeNil.getInstance()) {
            if (!(returnType instanceof TypeClass) && !(returnType instanceof TypeArray))
                throw new RuntimeException("semantic error");
            return null;
        }

		if (!returnType.isCompatibleWith(expType)) throw new RuntimeException("semantic error");
		return null;
	}
}
