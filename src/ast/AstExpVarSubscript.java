package ast;

import javax.lang.model.util.Elements;

import symboltable.SymbolTable;
import types.*;

public class AstExpVarSubscript extends AstExpVar
{


	// 	Accessing an array: Elements of an array can be accessed using v[e], with the following rules:
		// rule 1: The type of the variable v must be of an array.
		// rule 2: The subscript expression e must be of type int.
		// rule 3: If e is a constant expression, it must be greater than or equal to zero
		// rule 4: The resulting type is the element type T of the array that v is defined over.



	public AstExpVar var;
	public AstExp subscript;
	
	public AstExpVarSubscript(AstExpVar var, AstExp subscript)
	{
		System.out.print("====================== var -> var [ exp ]\n");
		// a[i] --> var: a , subscript: i
		
		this.var = var;
		this.subscript = subscript;
	}

	public void printMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		if (var != null) var.printMe();
		if (subscript != null) subscript.printMe();
	}

	public TypeList semantMe()
	{
		
		Type type = var.SemantMe();
		
		// cannot subscript a non array type
		if (!(type instanceof TypeArray))
            throw new RuntimeException("semantic error");
		






	}
}
