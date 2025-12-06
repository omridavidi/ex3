package ast;

import types.*;

public class AstExpVar extends AstExp
{
	public AstVar var;

	public AstExpVar(AstVar var)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.print("====================== exp -> var\n");

		this.var = var;
	}
	
	public void printMe()
	{
		System.out.print("AST EXP VAR\n");

		if (var != null) var.printMe();
		
		AstGraphviz.getInstance().logNode(
				serialNumber,
			"EXP VAR\n");

		AstGraphviz.getInstance().logEdge(serialNumber,var.serialNumber);
			
	}
}