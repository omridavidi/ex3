package ast;

import symboltable.SymbolTable;
import types.*;

public class AstCallExp extends AstExp
{
    public AstVar var;
	public String name;
    public AstExpList expList;
	
	public AstCallExp(AstVar var, String name, AstExpList expList)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

        if (var != null)
        {
            System.out.format("====================== var(%s) DOT ID(%s) ( expList(%s) )\n",var, name, expList);
        }
        else
        {
            System.out.format("======================ID(%s) ( expList(%s) )\n",name, expList);
        }

        this.var = var;
		this.name = name;
        this.expList = expList;
	}


	public void printMe()
	{
		System.out.format("AST CALL EXP\n");

		if (var != null) var.printMe();
		if (expList != null) expList.printMe();



		AstGraphviz.getInstance().logNode(
				serialNumber,
			"CALL EXP\n");

		if (var != null) AstGraphviz.getInstance().logEdge(serialNumber, var.serialNumber);
		if (expList != null) AstGraphviz.getInstance().logEdge(serialNumber, expList.serialNumber);
	}

	public Type SemantMe(){
		TypeFunction function;
		TypeList expectedParameters;
		TypeList providedParameters = null;
		
		if (expList != null) 
			{
				providedParameters = expList.SemantMe();
			}
			
		if (var == null){
			Type func = SymbolTable.getInstance().find(name);
			if (!(func instanceof TypeFunction)) throw new RuntimeException("semantic error");
			function = (TypeFunction) func;
			expectedParameters = function.params;
		}
		else{
			Type object = var.SemantMe();
			if (!(object))
		}
	}
}
