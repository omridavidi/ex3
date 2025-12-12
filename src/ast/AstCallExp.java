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
			// x.foo(1, 2)
		}
        else
        {
            System.out.format("======================ID(%s) ( expList(%s) )\n",name, expList);
			// foo(1, 2)
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

	public Type semantMe()
	{
		TypeFunction function = null;
		TypeList expectedParameters = null;
		TypeList providedParameters = null;
		
		if (expList != null) 
			{
				providedParameters = expList.semantMe();
			}
			
		if (var == null){
			Type func = SymbolTable.getInstance().find(name);
			if (!(func instanceof TypeFunction)) throw new RuntimeException("semantic error");
			function = (TypeFunction) func;
			expectedParameters = function.params;
		}
		else{
			Type object = var.semantMe();
			if (!(object instanceof TypeClass)) throw new RuntimeException("semantic error");
			TypeClass objectClass = (TypeClass) object;

			// find function in class data members with a certain name
			// if not found, go to father class and repeat

			Type foundDataMember = null;
			while(objectClass != null){
				if (objectClass.dataMembers != null){
					TypeClassVarDec varDecDataMember = objectClass.dataMembers.findElement(name);
					if (varDecDataMember != null){
						foundDataMember = varDecDataMember.type;
					}
					if (foundDataMember instanceof TypeFunction)  // current data member is a function 
						{
						function = (TypeFunction) foundDataMember;
						break;
						}
				}
				objectClass = objectClass.father;
			}
			if (function != null) expectedParameters = function.params;
		}
		
		TypeList expectedPointer = expectedParameters;
		TypeList providedPointer = providedParameters;
		
		while(expectedPointer != null && providedPointer != null)
		{
			if (!expectedPointer.head.isAssignableFrom(providedPointer.head)) throw new RuntimeException("semantic error");
			expectedPointer = expectedPointer.tail;
			providedPointer = providedPointer.tail;
		}

		if (expectedPointer != null || providedPointer != null) throw new RuntimeException("semantic error");

		return function.returnType;
	}
}
