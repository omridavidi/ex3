package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtAssign extends AstStmt
{
	public AstVar var;
	public AstExp exp;

	public AstStmtAssign(AstVar var, AstExp exp)
	{
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
		
        if (!t1.isAssignableFrom(t2)) throw new RuntimeException("semantic error");
		return null;
	}
}
