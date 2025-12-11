package ast;

import symboltable.SymbolTable;
import types.*;


public abstract class AstExpVar extends AstExp
{
    public abstract Type semantMe();
}
