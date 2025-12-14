package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public abstract class AstDec extends AstNode
{
	public AstDec(int lineNumber)
	{
		super(lineNumber);
	}

	public Type semantMe()
	{
		return null;
	}
}
