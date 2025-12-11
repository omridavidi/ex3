package ast;

import symboltable.SymbolTable;
import types.*;

public abstract class AstNewExp extends AstExp
{
    // this extends AstExp and being extended by AstNewExpArray and AstNewExpSimple
    public abstract Type semantMe();
}
