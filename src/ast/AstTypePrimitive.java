package ast;

import symboltable.SymbolTable;
import types.*;

public class AstTypePrimitive extends AstNode
{
	public String type;
	
	public AstTypePrimitive(String type)
	{
		serialNumber = AstNodeSerialNumber.getFresh();
	
		System.out.format("====================== type(%s)\n",type);
		// int
		
		this.type = type;
	}

	public void printMe()
	{
		System.out.format("AST TYPE( %s )\n",type);

		AstGraphviz.getInstance().logNode(
				serialNumber,
			String.format("TYPE : %s",type));
	}

	public Type semantMe(){
		switch (type){
			case "Int":
				return TypeInt.getInstance();
			case "String":
				return TypeString.getInstance();
			case "Void":
				return TypeVoid.getInstance();
			default:
				return null;
		}
	}
}
