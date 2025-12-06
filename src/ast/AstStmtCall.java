package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtCall extends AstStmt
{
	public AstCallExp call;

	public AstStmtCall(AstCallExp call)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> callExp;\n");

		this.call = call;
	}

	public void printMe()
	{
		System.out.print("AST STMT CALL\n");

		if (call != null) call.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
				"STMT CALL\n");

		if (call != null) AstGraphviz.getInstance().logEdge(serialNumber, call.serialNumber);
	}
}
