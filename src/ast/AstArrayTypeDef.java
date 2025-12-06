package ast;

import symboltable.SymbolTable;
import types.*;

public class AstArrayTypeDef extends AstDec
{
	public String name;
	public AstType type;

	public AstArrayTypeDef(String name, AstType type)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
	
		System.out.format("====================== arrayTypedef -> ARRAY ID = %s[];\n",type);

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

	public TypeArray SemantMe(){
		Type elementType = this.type.SemantMe();

		if (elementType == TypeVoid.getInstance()) throw new RuntimeException("semantic error");
		if (SymbolTable.getInstance().findInCurrentScope(name) != null) throw new RuntimeException("semantic error");

		TypeArray array = new TypeArray(name, elementType);
		SymbolTable.getInstance().enter(name, array);
		return array;
	}
}
