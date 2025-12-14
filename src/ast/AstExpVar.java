package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;


public abstract class AstExpVar extends AstExp
{
	public AstExpVar(int lineNumber)
	{
		super(lineNumber);
	}
    public abstract Type semantMe();
}
