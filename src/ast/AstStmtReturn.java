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
		Type returnType = SymbolTable.getInstance().find("__RET_TYPE__");
		
	}
}
