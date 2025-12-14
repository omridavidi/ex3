package ast;

import symboltable.SymbolTable;
import types.*;


public abstract class AstExp extends AstNode
{
	public AstExp(int lineNumber)
	{
		super(lineNumber);
	}
	/***********************************************/
	/* The default semantic action for an AST node */
	/***********************************************/
	public Type semantMe()
	{
		return null;
	}
}
