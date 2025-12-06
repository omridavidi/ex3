package ast;

import symboltable.SymbolTable;
import types.*;

public class AstVarDec extends AstDec
{
    public AstType type;
	public String name;
    public AstExp exp;

	public AstVarDec(AstType type, String name, AstExp exp)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

        if (exp != null)
        {
            System.out.format("====================== varDec -> TYPE ID(%s) := exp;\n", name);
        }
        else
        {
            System.out.format("====================== varDec -> TYPE ID(%s);\n",name);
        }

        this.type = type;
        this.name = name;
        this.exp = exp;

	}

	public void printMe()
	{

        System.out.print("AST VAR DEC\n");

        if(type != null)
            type.printMe();
        
        if(exp != null)
            exp.printMe();


        AstGraphviz.getInstance().logNode(serialNumber, String.format("AST VAR DEC (%s)\n", name));

        if(type != null) AstGraphviz.getInstance().logEdge(serialNumber,type.serialNumber);
        if(exp != null) AstGraphviz.getInstance().logEdge(serialNumber,exp.serialNumber);

	}
}