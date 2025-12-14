package ast;

import symboltable.SymbolTable;
import types.*;


public class AstExpString extends AstExp
{
	public String value;
	
	public AstExpString(String value, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== exp -> STRING( %s )\n", value);
		// "hello"
		
		this.value = value;
	}

	public void printMe()
	{
		System.out.format("AST STRING (%s)\n", value);
	
		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("STRING(%s)",value));		
	}

	public Type semantMe()
	{
		return TypeString.getInstance();
	}
}
