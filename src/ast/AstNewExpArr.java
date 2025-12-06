package ast;

public class AstNewExpArr extends AstNewExp
{
    public AstType type;
    public AstExp size;

    public AstNewExpArr(AstType type, AstExp size)
    {
        serialNumber = AstNodeSerialNumber.getFresh();
        System.out.print("====================== newExp -> NEW type [exp]\n");

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
}
