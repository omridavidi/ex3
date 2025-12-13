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
        // actual variable of type array
        // a := NEW int[10]; <-- from NEW till [10] 
        // int -> type
        // 10 -> size

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
        // Allocating an array: An array is allocated using new T[e], with the following rules:
        // rule 1: T is a previously declared type.
        // rule 2: The size expression e must be of type int.
        // rule 3: If e is a constant expression, it must be greater than 0.


        // rule 1: T is a previously declared type.
        Type t = type.semantMe();
        if (!(t instanceof TypeArray)) throw new RuntimeException("semantic error: cannot allocate array of non-array type");
        if (SymbolTable.getInstance().find(t.name) == null) throw new RuntimeException("semantic error: array type '" + t.name + "' not declared");
        
        // rule 2: The size expression e must be of type int.
        Type sizeType = size.semantMe();
        if (sizeType != TypeInt.getInstance()) throw new RuntimeException("semantic error: array size must be of type int");

        // rule 3: If e is a constant expression, it must be greater than 0.
        if (size instanceof AstExpInt) {
            AstExpInt sizeInt = (AstExpInt) size;
            if (sizeInt.value <= 0) throw new RuntimeException("semantic error: array size must be greater than 0");
        }
        return new TypeArray(t.name, t);
    }
}
