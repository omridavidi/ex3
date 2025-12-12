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

			// x -> var
			// foo -> name
			// (1, 2) -> expList
		}
        else
        {
            System.out.format("======================ID(%s) ( expList(%s) )\n",name, expList);
			// foo(1, 2)

			// foo -> name
			// (1, 2) -> expList
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

		// rule 1: Only previously defined functions or methods can be called.
		// rule 2: When calling a function or method, each argument must have a type compatible with the corresponding parameter in its signature.
		// rule 3: The type of a function or method call expression is the return type of the called function or method

		TypeFunction function = null;
		TypeList expectedParameters = null;
		TypeList providedParameters = null;
		
		if (expList != null) 
			{
				providedParameters = expList.semantMe();
			}
			
		if (var == null)
		{
			// FUNCTION CALL
			
			// rule 1: Only previously defined functions or methods can be called.
			// check if FUNCTION with name exists in symbol table
			Type varFunc = SymbolTable.getInstance().find(name);
			if (!(varFunc instanceof TypeFunction)) throw new RuntimeException("semantic error");
			function = (TypeFunction) varFunc;
			expectedParameters = function.params;
		}
		else{
			// METHOD CALL

			// rule 1: Only previously defined functions or methods can be called.
			// check if METHOD with name exists in symbol table


			// if object is not of class type throw error
			Type object = var.semantMe();
			if (!(object instanceof TypeClass)) throw new RuntimeException("semantic error");
			TypeClass objectClass = (TypeClass) object;

			// find function in class data members with a certain name
			// if not found, go to father class and repeat

			Type foundDataMember = null;
			while(objectClass != null){
				if (objectClass.dataMembers != null){
					foundDataMember = objectClass.dataMembers.findElement(name);

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

		// rule 2: When calling a function or method, each argument must have a type compatible with the corresponding parameter in its signature.
		// check parameters compatibility between expectedParameters and providedParameters

		while(expectedParameters != null && providedParameters != null)
		{
			if (!expectedParameters.head.isCompatibleWith(providedParameters.head))
				{
					throw new RuntimeException("semantic error");
				}
			expectedParameters = expectedParameters.tail;
			providedParameters = providedParameters.tail;
		}


		if (expectedParameters != null || providedParameters != null) throw new RuntimeException("semantic error");

		return function.returnType;

		// rule 3: The type of a function or method call expression is the return type of the called function or method
		// checked outside of this semantme
	}
}
