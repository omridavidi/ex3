package ast;

public class AstNewExpSimple extends AstNewExp
{
    public AstType type;

    public AstNewExpSimple(AstType type)
    {
        serialNumber = AstNodeSerialNumber.getFresh();
        System.out.print("====================== newExp -> NEW type\n");
        
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
}
