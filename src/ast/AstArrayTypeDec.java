package ast;

import Exception.SemanticException;
import symboltable.SymbolTable;
import types.*;

public class AstArrayTypeDec extends AstDec
{
	public String name;
	public AstType type;

	public AstArrayTypeDec(String name, AstType type, int lineNumber)
	{
		super(lineNumber);
		serialNumber = AstNodeSerialNumber.getFresh();
	
		System.out.format("====================== arrayTypedec -> ARRAY ID = %s[];\n",type);
		// array int_array = int[]

		this.name = name;
		this.type = type;
	}

	public void printMe()
	{

		System.out.format("AST ARRAY TYPE DEF NAME: %s, TYPE\n",this.name);

        if (type != null) type.printMe();


		AstGraphviz.getInstance().logNode(serialNumber, String.format("ARRAY TYPEDEF : %s",name));
        
        if(type != null) AstGraphviz.getInstance().logEdge(serialNumber,type.serialNumber);
	}



	public TypeArray semantMe(){

		// Check for reserved keywords
		if (SymbolTable.isReservedKeyword(name)) throw new SemanticException(this.getLineNumber(), "array type '" + name + "' cannot use reserved keyword");

		// Array type declarations can only appear in global scope
		if (!SymbolTable.getInstance().getScope().equals("GLOBAL")) throw new SemanticException(this.getLineNumber(), "array type '" + name + "' can only be declared in global scope");

		// rule 1: type must not be void
		// rule 2: An array type must be defined over a previously declared (non-void) type.

		Type elementType = this.type.semantMe();

		// rule 1: type must not be void
		if (elementType == TypeVoid.getInstance())
			{
				throw new SemanticException(this.getLineNumber(), "array type '" + name + "' cannot be defined over void type");
			}
		
		// rule 2: An array type must be defined over a previously declared (non-void) type.
		if (SymbolTable.getInstance().lookupLocal(name) != null)
			{
				throw new SemanticException(this.getLineNumber(), "array type '" + name + "' already declared in current scope");
			}

		TypeArray array = new TypeArray(name, elementType);
		SymbolTable.getInstance().enter(name, array);
		return array;
	}
}
