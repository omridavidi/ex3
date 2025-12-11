package ast;

import symboltable.SymbolTable;
import types.*;

public abstract class AstVar extends AstNode
{
    public abstract Type semantMe();
}
