package ast;

import symboltable.SymbolTable;
import types.*;

public class AstcFieldList extends AstNode
{
	public AstcField head;
	public AstcFieldList tail;

	public AstcFieldList(AstcField cField, AstcFieldList cFieldList, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		if (cField == null) System.out.print("====================== cFieldList -> cField\n");
        else System.out.print("====================== cFieldList -> cField cFieldList\n");
		/* class point{
			int x; <-- cField 
			int y; <-- cField 
			}
		*/
        this.head = cField;
		this.tail = cFieldList;
	}

	public void printMe()
	{
		System.out.format("AST CFIELD LIST\n");

		if (head != null) head.printMe();
		if (tail != null) tail.printMe();

		AstGraphviz.getInstance().logNode(serialNumber, "CFIELD LIST\n");
		
		if (head != null) AstGraphviz.getInstance().logEdge(serialNumber,head.serialNumber);
		if (tail != null) AstGraphviz.getInstance().logEdge(serialNumber,tail.serialNumber);
	}
	
	public Type semantMe(){
		if (head != null) head.semantMe();
		if (tail != null) tail.semantMe();
		return null;
	}
}
