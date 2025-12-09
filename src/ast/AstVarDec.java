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

    public Type semantMe(){
		Type t;
	
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SymbolTable.getInstance().find(type);
		if (t == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,type);
			System.exit(0);
		}
		
		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SymbolTable.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,name);				
		}

		/************************************************/
		/* [3] Enter the Identifier to the Symbol Table */
		/************************************************/
		SymbolTable.getInstance().enter(name,t);

		/************************************************************/
		/* [4] Return value is irrelevant for variable declarations */
		/************************************************************/
		return null;		
	}
}