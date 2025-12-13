package ast;

import types.*;
import symboltable.*;

public class AstExpVarSimple extends AstExpVar
{
	public String name;
	
	public AstExpVarSimple(String name)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);
		// x
		
		this.name = name;
	}

	public void printMe()
	{
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		AstGraphviz.getInstance().logNode(
                serialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}

	public Type semantMe()
	{
		Type t = SymbolTable.getInstance().find(name);
		if (t != null) return t;
		throw new RuntimeException("semantic error: variable '" + name + "' not declared");
	}
}