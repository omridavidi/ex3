package ast;

import symboltable.SymbolTable;
import types.*;

public class AstVarSimple extends AstVar
{
	public String name;
	
	public AstVarSimple(String name)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);
		// x
		
		this.name = name;
	}

	public void printMe()
	{
		System.out.format("AST VAR SIMPLE( %s )\n",name);

		AstGraphviz.getInstance().logNode(serialNumber, String.format("SIMPLE VAR\n(%s)",name));
	}

	public Type semantMe()
	{
		Type t = SymbolTable.getInstance().find(name);
		if (t == null) throw new RuntimeException("semantic error");
		return t;
	}
}
