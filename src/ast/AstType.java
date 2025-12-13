package ast;

import symboltable.SymbolTable;
import types.*;

public abstract class AstType extends AstNode
{
    public abstract Type semantMe();
    
}
