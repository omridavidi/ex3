package ast;

import symboltable.SymbolTable;
import types.*;

public class AstcField extends AstNode
{
	public AstDec dec;
	
	public AstcField(AstDec dec)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== cField -> Dec)\n");

		this.dec = dec;
	}

	public void printMe()
	{
		System.out.format("AST cField\n");

		if (dec != null) dec.printMe();

		AstGraphviz.getInstance().logNode(serialNumber, String.format("cField"));
		if (dec != null) AstGraphviz.getInstance().logEdge(serialNumber,dec.serialNumber);
	}
}
