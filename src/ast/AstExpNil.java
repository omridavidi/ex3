package ast;

import symboltable.SymbolTable;
import types.*;


public class AstExpNil extends AstExp
{
	
	public AstExpNil()
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== exp -> NIL\n");
	}

	public void printMe()
	{
		System.out.format("AST NIL\n");

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("NIL"));
	}
}
