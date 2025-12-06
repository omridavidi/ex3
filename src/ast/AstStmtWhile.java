package ast;

import symboltable.SymbolTable;
import types.*;

public class AstStmtWhile extends AstStmt
{
	public AstExp condition;
	public AstStmtList body;

	public AstStmtWhile(AstExp condition, AstStmtList body)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> WHILE (exp) {stmtList}\n");

		this.condition = condition;
		this.body = body;
	}

	public void printMe()
	{
		System.out.print("AST STMT WHILE\n");

		if (condition != null) condition.printMe();
		if (body != null) body.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
				"WHILE\n");

		if (condition != null) AstGraphviz.getInstance().logEdge(serialNumber, condition.serialNumber);
		if (body != null) AstGraphviz.getInstance().logEdge(serialNumber, body.serialNumber);
	}
}