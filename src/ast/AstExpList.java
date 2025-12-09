package ast;

import symboltable.SymbolTable;
import types.*;

public class AstExpList extends AstNode
{
	public AstExp head;
	public AstExpList tail;

	public AstExpList(AstExp head, AstExpList tail)
	{

		serialNumber = AstNodeSerialNumber.getFresh();

		if (tail != null) System.out.print("====================== ExpList -> Exp ExpList\n");

		this.head = head;
		this.tail = tail;
	}

	public void printMe()
	{
		System.out.print("AST Exp LIST\n");

		if (head != null) head.printMe();
		if (tail != null) tail.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"EXP LIST\n");
		
		if (head != null) AstGraphviz.getInstance().logEdge(serialNumber,head.serialNumber);
		if (tail != null) AstGraphviz.getInstance().logEdge(serialNumber,tail.serialNumber);
	}

	public TypeList semantMe(){
		Type headType = null;
		if (head != null) headType = head.semantMe();

		TypeList tailList = null;
		if (tail != null) tailList = tail.semantMe();

		return new TypeList(headType, tailList);	
	}
}
