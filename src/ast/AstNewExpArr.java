package ast;

import symboltable.SymbolTable;
import types.*;

public class AstNewExpArr extends AstNewExp
{
    public AstType type;
    public AstExp size;

    public AstNewExpArr(AstType type, AstExp size)
    {
        serialNumber = AstNodeSerialNumber.getFresh();
        System.out.print("====================== newExp -> NEW type [exp]\n");
        // a := NEW int[10]; <-- from NEW till [10] 

        this.type  = type;
        this.size  = size;
    }

    public void printMe()
    {
        System.out.print("AST NEW EXP (ARRAY)\n");

        if (type != null) type.printMe();
        if (size != null) size.printMe();

        AstGraphviz.getInstance().logNode(
                serialNumber,
                "NEW ARRAY\n");

        if (type != null) AstGraphviz.getInstance().logEdge(serialNumber, type.serialNumber);
        if (size != null) AstGraphviz.getInstance().logEdge(serialNumber, size.serialNumber);
    }

    public Type semantMe(){
        Type t = type.semantMe();
        if (t = TypeVoid.getInstance()) throw new RuntimeException("semantic error");
       
        Type sizeType = size.semantMe();
        if (sizeType != TypeInt.getInstance()) throw new RuntimeException("semantic error");

        if (size instanceof AstExpInt) {
            AstExpInt sizeInt = (AstExpInt) size;
            if (sizeInt.value <= 0) throw new RuntimeException("semantic error");
        }
    }
}
