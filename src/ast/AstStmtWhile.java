package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public class AstStmtWhile extends AstStmt
{
	public AstExp condition;
	public AstStmtList body;

	public AstStmtWhile(AstExp condition, AstStmtList body, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== stmt -> WHILE (exp) {stmtList}\n");
		/* WHILE (i < 10) {
        //     i := i + 1;
        } */

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

	public Type semantMe(){
		Type condType = condition.semantMe();
		if (condType != TypeInt.getInstance()) throw new SemanticException(this.getLineNumber(), "while condition must be of type int");

		SymbolTable.getInstance().beginScope();
        if (body != null) body.semantMe();
        SymbolTable.getInstance().endScope();
		
		return null;
	}
}