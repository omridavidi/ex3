package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;


public class AstExpInt extends AstExp
{
	public int value;
	
	public AstExpInt(int value, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== exp -> INT( %d )\n", value);
		// 5
		
		this.value = value;
	}

	public void printMe()
	{
		System.out.format("AST INT( %d )\n",value);

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("INT(%d)",value));
	}

	public Type semantMe()
	{
		return TypeInt.getInstance();
	}
}
