package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public class AstNewExpSimple extends AstNewExp
{
    public AstType type;

    public AstNewExpSimple(AstType type, int lineNumber)
    {
        super(lineNumber);
        serialNumber = AstNodeSerialNumber.getFresh();
        System.out.print("====================== newExp -> NEW type\n");
        // p := NEW Point; <-- "NEW Point" is NewExpSimple

        this.type = type;
    }

    public void printMe()
    {
        System.out.print("AST NEW EXP (SIMPLE)\n");

        if (type != null) type.printMe();

        AstGraphviz.getInstance().logNode(
                serialNumber,
                "NEW SIMPLE\n");

        if (type != null) AstGraphviz.getInstance().logEdge(serialNumber, type.serialNumber);
    }

    public Type semantMe(){
        Type t = type.semantMe();
        if (!(t instanceof TypeClass)) throw new SemanticException(this.getLine(), "cannot instantiate non-class type");

        return t;
    }
}
