package ast;

import symboltable.SymbolTable;
import types.*;

public abstract class AstType extends AstNode
{
	public AstType(int lineNumber)
	{
		super(lineNumber);
	}
    public abstract Type semantMe();
    
}
