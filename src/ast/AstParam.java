package ast;

import symboltable.SymbolTable;
import types.*;

public class AstParam extends AstNode
{
	public AstType type;
	public String name;
	
	public AstParam(AstType type, String name)
	{
		serialNumber = AstNodeSerialNumber.getFresh();

		System.out.format("====================== parameter -> type ID(%s)\n", name);
		//	int add(int x) {}
		this.type = type;
		this.name = name;
	}

	public void printMe()
	{
		System.out.format("AST PARAMETER (%s)\n", name);
		if (type != null) type.printMe();

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("PARAMETER(%s)\n",name));
		
		if (type != null) AstGraphviz.getInstance().logEdge(serialNumber,type.serialNumber);
	}

	public Type semantMe(){
		Type t = type.semantMe();
		if (t == TypeVoid.getInstance()) throw new RuntimeException("semantic error: parameter '" + name + "' cannot be of type void");

		SymbolTable.getInstance().enter(name, t);
		return t;
	}
}
