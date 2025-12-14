package ast;

import types.*;
import symboltable.*;

public class AstExpVarSimple extends AstExpVar
{
	public AstVar var;
	
	public AstExpVarSimple(AstVar var)
	{
		// x
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== exp -> var\n");

		this.var = var;
	}


	public void printMe()
	{
		System.out.format("AST NODE SIMPLE VAR\n");

		AstGraphviz.getInstance().logNode(
                serialNumber,
			String.format("SIMPLE\nVAR\n"));
	}

	public Type semantMe()
	{
		Type t = var.semantMe();
		return t;
	}
}