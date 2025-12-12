package ast;

import javax.lang.model.util.Elements;

import symboltable.SymbolTable;
import types.*;

public class AstVarSubscript extends AstExpVar
{
	// 	Accessing an array: Elements of an array can be accessed using v[e], with the following rules:
		// rule 1: The type of the variable v must be of an array.
		// rule 2: The subscript expression e must be of type int.
		// rule 3: If e is a constant expression, it must be greater than or equal to zero
		// rule 4: The resulting type is the element type T of the array that v is defined over.

	public AstExpVar var;
	public AstExp subscript;
	
	public AstVarSubscript(AstExpVar var, AstExp subscript)
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

	public Type semantMe()
	{
		
		Type type = var.semantMe();
		
		// rule 1: The type of the variable v must be of an array.
		if (!(type instanceof TypeArray))
            throw new RuntimeException("semantic error");
		
		// rule 2: The subscript expression e must be of type int.
        Type subscriptType = subscript.semantMe();
        if (subscriptType != TypeInt.getInstance())
            throw new RuntimeException("semantic error");

		// rule 3: If e is a constant expression, it must be greater than or equal to zero
		if (subscript instanceof AstExpInt)
		{
			AstExpInt constExp = (AstExpInt) subscript;
			if (constExp.value < 0)
				throw new RuntimeException("semantic error");
		}

		// rule 4: The resulting type is the element type T of the array that v is defined over.
		return ((TypeArray) type).arrayDataType;
	}
}
