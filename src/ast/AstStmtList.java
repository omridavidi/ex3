package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;


public class AstStmtList extends AstNode
{
	public AstStmt head;
	public AstStmtList tail;

	public AstStmtList(AstStmt head, AstStmtList tail, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
		/* {
				x := 5;
				y := x + 1;
		} */

		if (tail == null) System.out.print("====================== stmts -> stmt      \n");
		/* {
				x := 5;
		} */

		this.head = head;
		this.tail = tail;
	}

	public void printMe()
	{
		System.out.print("AST NODE STMT LIST\n");

		if (head != null) head.printMe();
		if (tail != null) tail.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"STMT\nLIST\n");

		if (head != null) AstGraphviz.getInstance().logEdge(serialNumber,head.serialNumber);
		if (tail != null) AstGraphviz.getInstance().logEdge(serialNumber,tail.serialNumber);
	}
	
	public Type semantMe()
	{
		if (head != null) head.semantMe();
		if (tail != null) tail.semantMe();
		
		return null;
	}
}
