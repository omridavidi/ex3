package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;


public class AstDecList extends AstNode
{
	public AstDec head;
	public AstDecList tail;

	public AstDecList(AstDec head, AstDecList tail, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		if (tail != null) System.out.print("====================== decList -> dec decList\n");
		/* CLASS Point {
				int x;
				int y;
			}

			int foo(int a) {
				return a + x;
			}
		*/
		this.head = head;
		this.tail = tail;
	}

	public void printMe()
	{
		System.out.print("AST DEC LIST\n");

		if (head != null) head.printMe();
		if (tail != null) tail.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"DEC LIST\n");
		
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
