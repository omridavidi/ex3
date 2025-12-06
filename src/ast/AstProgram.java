package ast;

import symboltable.SymbolTable;
import types.*;

public class AstProgram extends AstNode
{
	public AstDecList decList;

	public AstProgram(AstDecList decList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		if (decList != null) System.out.print("====================== program -> decList\n");

		this.decList = decList;
	}

	public void printMe()
	{
		System.out.print("AST AstProgram\n");

		if (decList != null) decList.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			"PROGRAM\n");
		
		if (decList != null) AstGraphviz.getInstance().logEdge(serialNumber,decList.serialNumber);
	}
	
}
