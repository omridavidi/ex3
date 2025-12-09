package ast;

import symboltable.SymbolTable;
import types.*;

public class AstExpVarSubscript extends AstExpVar
{
	public AstExpVar var;
	public AstExp subscript;
	
	public AstExpVarSubscript(AstExpVar var, AstExp subscript)
	{
		System.out.print("====================== var -> var [ exp ]\n");
		this.var = var;
		this.subscript = subscript;
	}

	public void printMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		if (var != null) var.printMe();
		if (subscript != null) subscript.printMe();
	}

	public TypeList semantMe(){
		Type headType = null;
		if (var != null) headType = var.semantMe();

		TypeList tailList = null;
		if (subscript != null) tailList = subscript.semantMe();

		return new TypeList(headType, tailList);	
	}
}
