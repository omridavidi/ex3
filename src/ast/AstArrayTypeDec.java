package ast;

import symboltable.SymbolTable;
import types.*;

public class AstArrayTypeDec extends AstDec
{
	public String name;
	public AstType type;

	public AstArrayTypeDec(String name, AstType type)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
	
		System.out.format("====================== arrayTypedef -> ARRAY ID = %s[];\n",type);
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

		// rule 1: type must not be void
		// rule 2: An array type must be defined over a previously declared (non-void) type.

		Type elementType = this.type.semantMe();

		// rule 1: type must not be void
		if (elementType == TypeVoid.getInstance())
			{
				throw new RuntimeException("semantic error");
			}
		
		// rule 2: An array type must be defined over a previously declared (non-void) type.
		if (SymbolTable.getInstance().findInCurrentScope(name) != null)
			{
				throw new RuntimeException("semantic error");
			}

		TypeArray array = new TypeArray(name, elementType);
		SymbolTable.getInstance().enter(name, array);
		return array;
	}
}
