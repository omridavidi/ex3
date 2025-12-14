package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public abstract class AstStmt extends AstNode
{
	public AstStmt(int lineNumber)
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
