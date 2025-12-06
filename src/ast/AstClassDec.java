package ast;

import symboltable.SymbolTable;
import types.*;

public class AstClassDec extends AstDec
{
	public String name;
    public String superName;
    public AstcFieldList cFieldList;

	public AstClassDec(String name, String superName, AstcFieldList cFieldList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

        if (superName != null)
        {
            System.out.format("====================== classDec -> CLASS ID(%s) EXTENDS ID(%s) { cFieldList } \n",name, superName);
        }
        else
        {
            System.out.format("====================== classDec -> CLASS ID(%s) { cFieldList } \n",name);
        }

        this.name = name;
        this.superName = superName;
        this.cFieldList = cFieldList;

	}

	public void printMe()
	{
        System.out.print("AST CLASS DEC\n");

        if(cFieldList != null)
            cFieldList.printMe();

        String temp;

        if (superName != null)
            temp = "AST CLASS DEC ("+ name +") EXTENDS ("+superName+")\n";
        else
            temp = "AST CLASS DEC ("+name+")\n";

        AstGraphviz.getInstance().logNode(serialNumber, temp);

        if(cFieldList != null) AstGraphviz.getInstance().logEdge(serialNumber,cFieldList.serialNumber);


	}
}








